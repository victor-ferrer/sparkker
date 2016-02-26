package org.vferrer.sparkker.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.vferrer.sparkker.service.AnalyzeService;
import org.vferrer.sparkker.service.RulesEngine;
import org.vferrer.sparkker.service.facts.Operation;
import org.vferrer.sparkker.service.facts.Position;
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
	
	@Autowired
	RulesEngine droolsService;

	@Value("${eureka.client.serviceUrl.defaultZone}")
	String baseEurekaPath;

	@Autowired
	private AnalyzeService analyzeService;

	@Value("${data.feed.online}")
	private boolean useOnlineFeed;

	@RequestMapping("/analyzeStock")
	public ChartData analyzeQuote(String ticker, Integer windowSize) throws Exception {

		// Get the desired quotes
		List<StockQuotationJPA> stocks = useOnlineFeed ? loadQuotesFromStokker(ticker):loadQuotesFromFile(ticker);

		// Set up the analysis job
		Set<Indicator> indicators = new HashSet<>();
		indicators.add(IndicatorsFactory.max(Granularity.DAY, windowSize));
		indicators.add(IndicatorsFactory.sma(Granularity.DAY, windowSize));
		indicators.add(IndicatorsFactory.min(Granularity.DAY, windowSize));

		List<AnalizedStockQuotation> quotations = analyzeService.analyzeStockQuotations(stocks, indicators,windowSize);

		// FIXME data is coming in the wrong order, why?
		Collections.reverse(quotations);
		
		// Run the business rules over the data + indicators
		List<Position> operations = droolsService.executeRules(quotations,null);
		
		for (Position position : operations) {
			System.out.println(String.format("BUY: Price: %s Date: %s Score: %s ",
																	position.getOpeningOperation().getPrice().toString(),
																	position.getOpeningOperation().getDate().toString(),
																	position.getOpeningOperation().getScore().toString()));
			
			
			System.out.println(String.format("SELL: Price: %s Date: %s Score: %s ",
																	position.getClosingOperation().getPrice().toString(),
																	position.getClosingOperation().getDate().toString(),
																	position.getClosingOperation().getScore().toString()));
			
			System.out.println("Yield: " + NumberFormat.getPercentInstance().format(position.getYield()/100d));
		}
		// Build the chart data
		ChartData toReturn = buildChartData(quotations, null);
		
		return toReturn;
	}

	
	@RequestMapping(path="/jobs/submit", method = RequestMethod.POST)
	public ChartData analyzeQuote(@RequestBody JobParams jobConfig) throws Exception {

		// Get the desired quotes
		List<StockQuotationJPA> stocks = useOnlineFeed ? loadQuotesFromStokker(jobConfig.getTargetStock()):loadQuotesFromFile(jobConfig.getTargetStock());

		// Set up the analysis job
		int windowSize = Integer.parseInt(jobConfig.getSmaWindow());
		Set<Indicator> indicators = new HashSet<>();
		indicators.add(IndicatorsFactory.max(Granularity.DAY, windowSize));
		indicators.add(IndicatorsFactory.sma(Granularity.DAY, windowSize));
		indicators.add(IndicatorsFactory.min(Granularity.DAY, windowSize));

		List<AnalizedStockQuotation> quotations = analyzeService.analyzeStockQuotations(stocks, indicators,windowSize);

		// FIXME data is coming in the wrong order, why?
		Collections.reverse(quotations);
		
		// Run the business rules over the data + indicators
		List<Position> positions = droolsService.executeRules(quotations, jobConfig);
		
		// Build the chart data
		final ChartData toReturn = new ChartData();
		toReturn.setPositions(positions);		
		return toReturn;
	}
	
	/**
	 * Utility method for loading stocks without stokker. Some sample flies are used instead
	 * @param ticker
	 * @return
	 */
	private List<StockQuotationJPA> loadQuotesFromFile(String ticker) 
	{
		InputStream fileIS = SparkkerController.class.getResourceAsStream("/data/" + ticker);
		
		if (fileIS != null){
			
			try {
				List<String> lines = IOUtils.readLines(fileIS);
				
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
		else {
			System.out.println("WARN: Could not find the sample data file for ticker " + ticker);
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
	 * @param operations 
	 * @param scorings 
	 * @return
	 */
	private ChartData buildChartData(List<AnalizedStockQuotation> quotations, List<Operation> operations) {
		
		final ChartData toReturn = new ChartData();
		toReturn.setLabels(new ArrayList<>());
		toReturn.setLabelsVoting(new ArrayList<>());
		
		//toReturn.setOperations(operations);
		
		toReturn.setSeries(Arrays.asList("Price", "SMA(200)", "MAX200", "MIN200"));
		toReturn.setSeriesVoting(Arrays.asList("SCORE"));
		
		// FIXME How should manage the labels
		for (int i = 0; i < quotations.size(); i++) {
			toReturn.getLabels().add(String.valueOf(i));
			toReturn.getLabelsVoting().add(String.valueOf(i));
		}

		List<Double> priceData = new ArrayList<>();
		List<Double> smaData = new ArrayList<>();
		List<Double> maxData = new ArrayList<>();
		List<Double> minData = new ArrayList<>();

		quotations.stream().forEach(s -> priceData.add(s.getValue()));
		quotations.stream().forEach(s -> smaData.add(s.getIndicators().get("SMA").getValue()));
		quotations.stream().forEach(s -> maxData.add(s.getIndicators().get("MAX").getValue()));
		quotations.stream().forEach(s -> minData.add(s.getIndicators().get("MIN").getValue()));

		toReturn.setDatasets(Arrays.asList(priceData, smaData, maxData, minData));

		// Voting char
		List<Double> values = new ArrayList<>();
		quotations.stream().forEach(s -> values.add(s.getIndicators().get("SCORE").getValue()));
		toReturn.setDatasetsVoting(Arrays.asList(values));
		
		
		return toReturn;
	}

}
