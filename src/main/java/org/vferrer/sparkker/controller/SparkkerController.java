package org.vferrer.sparkker.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@RestController
public class SparkkerController {

//	@Autowired
//	 private StokkerClient stokkerClient;

	@Autowired
	RestTemplate rt;
	
	@Value("${eureka.client.serviceUrl.defaultZone}")
	String baseEurekaPath;
	
	@Autowired
	private AnalyzeService analyzeService;

	@RequestMapping("/analyzeStock")
	public List<AnalizedStockQuotation> analyzeQuote(String ticker) throws IOException 
	{

		String url = String.format("http://localhost:8989/stockQuotationJPAs/search/findValueByStock?ticker=%s",ticker);
		
		try {
			ResponseEntity<StockQuotationJPAPagedResources> response = rt.getForEntity(url,StockQuotationJPAPagedResources.class);
			
			System.out.println(response.getStatusCode());
			
			if (HttpStatus.OK == response.getStatusCode()){
				
				List<StockQuotationJPA> stocks = new ArrayList<>(response.getBody().getContent());
				
				System.out.println("Retrived stock quoations count: " + stocks.size());
				
				Set<Indicator> indicators = new HashSet<>();
				indicators.add(IndicatorsFactory.max(Granularity.DAY, 200));
				indicators.add(IndicatorsFactory.sma(Granularity.DAY, 200));
				
				return analyzeService.analyzeStockQuotations(stocks,indicators);
			}
			else {
				System.out.println("Error retrieving the stock quotations: " + response.getStatusCode());
				return Collections.emptyList();
			}
		}
		catch(RestClientException rex){
			System.out.println("Error retrieving the stock quotations: " + rex.getMessage());
			return Collections.emptyList();
		}
	}


	
}
