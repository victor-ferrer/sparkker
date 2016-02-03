package org.vferrer.sparkker.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vferrer.sparkker.service.AnalyzeService;
import org.vferrer.sparkker.stokker.StockQuotation;
import org.vferrer.sparkker.stokker.StokkerClient;

@RestController
public class SparkkerController {
	
	@Autowired
	private StokkerClient stokkerClient;

	@Autowired
	private AnalyzeService analyzeService;

	@RequestMapping("/analyzeStock")
	public List<StockQuotation> analyzeQuote(String ticker, String windowSize) throws IOException 
	{
		Resources<StockQuotation> quotations = stokkerClient.getAllStockQuotations(ticker);

		List<StockQuotation> stockList = new ArrayList<>(quotations.getContent());
		
		return analyzeService.analyzeStockQuotations(stockList,Integer.parseInt(windowSize));
	}
}
