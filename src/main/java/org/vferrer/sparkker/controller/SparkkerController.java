package org.vferrer.sparkker.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.vferrer.sparkker.service.AnalyzeService;
import org.vferrer.sparkker.stokker.StockQuotation;
import org.vferrer.sparkker.stokker.StokkerClient;

@RestController
public class SparkkerController {
	// @Autowired
	// private StokkerClient stokkerClient;

	@Autowired
	private AnalyzeService analyzeService;

	// @RequestMapping("/getLastQuoteForStock")
	// public StockQuotation getLastQuoteForStock(@Param("ticker") String
	// ticker){
	// return stokkerClient.getLastStockPrice(ticker);
	// }

	@RequestMapping("/analyzeStock")
	public List<StockQuotation> analyzeQuote() throws IOException {
		// List<StockQuotation> quotations =
		// stokkerClient.getAllStockQuotations(ticker);
		//
//		RestTemplate restTemplate = new RestTemplate();
//		
//		String url = String.format("http://localhost:8989/stockQuotationJPAs/search/findValueByStock?ticker=%s",ticker);
//		
//		ResponseEntity<Resource<StockQuotation>> responseEntity = restTemplate.exchange(url,HttpMethod.GET, null,
//				new ParameterizedTypeReference<Resource<StockQuotation>>() {
//				}, Collections.emptyMap());
//		if (responseEntity.getStatusCode() == HttpStatus.OK) {
//			Resource<StockQuotation> customerResource = responseEntity.getBody();
//			StockQuotation customer = customerResource.getContent();
//		}
//
//		RestTemplate rt = new RestTemplate();
//
//		ResponseEntity<StockQuotation[]> responseEntity = rt.getForEntity(URI.create(url), StockQuotation[].class);
//
//		StockQuotation[] body = responseEntity.getBody();

		return analyzeService.analyzeStockQuotations(200);
	}
}
