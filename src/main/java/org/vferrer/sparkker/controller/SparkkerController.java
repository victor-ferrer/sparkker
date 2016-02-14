package org.vferrer.sparkker.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.vferrer.sparkker.service.AnalyzeService;
import org.vferrer.sparkker.service.indicators.IndicatorsFactory;
import org.vferrer.sparkker.stokker.AnalizedStockQuotation;
import org.vferrer.sparkker.stokker.Indicator;
import org.vferrer.sparkker.stokker.Indicator.Granularity;
import org.vferrer.sparkker.stokker.StockQuotationJPA;
import org.vferrer.sparkker.stokker.StockQuotationJPAPagedResources;

import com.clearspring.analytics.util.Lists;

@RestController
public class SparkkerController {

	// @Autowired
	// private StokkerClient stokkerClient;

	@Autowired
	RestTemplate rt;

	@Value("${eureka.client.serviceUrl.defaultZone}")
	String baseEurekaPath;

	@Autowired
	private AnalyzeService analyzeService;

	@Value("${data.feed.online}")
	private boolean useOnlineFeed;

	@RequestMapping("/analyzeStock")
	public ChartData analyzeQuote(String ticker) throws IOException {

		// Get the desired quotes
		List<StockQuotationJPA> stocks = useOnlineFeed ? loadQuotesFromStokker(ticker):loadQuotesFromFile(ticker);

		// Set up the analysis job
		Set<Indicator> indicators = new HashSet<>();
		indicators.add(IndicatorsFactory.max(Granularity.DAY, 200));
		indicators.add(IndicatorsFactory.sma(Granularity.DAY, 200));
		indicators.add(IndicatorsFactory.min(Granularity.DAY, 200));

		List<AnalizedStockQuotation> quotations = analyzeService.analyzeStockQuotations(stocks, indicators);

		// Build the chart data
		ChartData toReturn = buildChartData(quotations);
		return toReturn;
	}

	
	/**
	 * Utility method for loading stocks without stokker. Some sample flies are used instead
	 * @param ticker
	 * @return
	 */
	private List<StockQuotationJPA> loadQuotesFromFile(String ticker) 
	{
		
		URL fileURL = SparkkerController.class.getResource("/data/" + ticker);
		
		if (fileURL != null){
			
			try {
				List<String> lines = FileUtils.readLines(new File(fileURL.toURI()));
				
				List<StockQuotationJPA> toReturn = new ArrayList<>();
				for (String line : lines) {
					toReturn.add(StockQuotationJPA.fromLine(line));
				}
				
				return toReturn;
				
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return Lists.newArrayList();
	}

	/**
	 * Utility method encapsulating the retrieval on the stocks quotations from stokker using a RestTemplate
	 * TODO Should be better off in a separated bean
	 * @param ticker
	 * @return
	 */
	private List<StockQuotationJPA> loadQuotesFromStokker(String ticker) {
		
		// FIXME extract this to a configuration parameter
		String url = String.format("http://localhost:8989/stockQuotationJPAs/search/findValueByStock?ticker=%s",ticker);

		try {
			ResponseEntity<StockQuotationJPAPagedResources> response = rt.getForEntity(url,
					StockQuotationJPAPagedResources.class);

			System.out.println(response.getStatusCode());

			if (HttpStatus.OK != response.getStatusCode()) {
				System.out.println("Error retrieving the stock quotations: " + response.getStatusCode());
				return null;
			}

			List<StockQuotationJPA> stocks = new ArrayList<>(response.getBody().getContent());
			
			return stocks;
		}

		catch (RestClientException rex) {
			System.out.println("Error retrieving the stock quotations: " + rex.getMessage());
			return null;
		}

	}

	/**
	 * Utility function that translates the data from Spark into the custom
	 * format used by the chart library
	 * 
	 * @param quotations
	 * @return
	 */
	private ChartData buildChartData(List<AnalizedStockQuotation> quotations) {
		new ChartData();

		final ChartData toReturn = new ChartData();
		toReturn.setLabels(new ArrayList<>());
		toReturn.setSeries(Arrays.asList("Price", "SMA(200)", "MAX200", "MIN200"));

		// FIXME How should manage the labels
		for (int i = 0; i < quotations.size(); i++) {
			toReturn.getLabels().add(String.valueOf(i));
		}

		List<Double> priceData = new ArrayList<>();
		List<Double> smaData = new ArrayList<>();
		List<Double> maxData = new ArrayList<>();
		List<Double> minData = new ArrayList<>();

		// FIXME data is coming in the wrong order, why?
		Collections.reverse(quotations);

		quotations.stream().forEach(s -> priceData.add(s.getValue()));
		quotations.stream().forEach(s -> smaData.add(s.getIndicators().get("SMA").getValue()));
		quotations.stream().forEach(s -> minData.add(s.getIndicators().get("MAX").getValue()));
		quotations.stream().forEach(s -> maxData.add(s.getIndicators().get("MIN").getValue()));

		toReturn.setDatasets(Arrays.asList(priceData, smaData, maxData, minData));

		return toReturn;
	}

}
