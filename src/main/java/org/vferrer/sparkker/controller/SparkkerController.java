package org.vferrer.sparkker.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vferrer.sparkker.service.AnalyzeService;
import org.vferrer.sparkker.stokker.StockQuotation;
import org.vferrer.sparkker.stokker.StokkerClient;

@RestController
public class SparkkerController 
{
	@Autowired
	private StokkerClient stokkerClient;
	
	@Autowired
	private AnalyzeService analyzeService;
	
	@RequestMapping("/getLastQuoteForStock")
	public StockQuotation getLastQuoteForStock(@Param("ticker") String ticker){
		return stokkerClient.getLastStockPrice(ticker);
	}
	
	@RequestMapping("/analyzeQuote")
	public Long analyzeQuote(@Param("ticker") String ticker){
		List<StockQuotation> quotations = stokkerClient.getAllStockQuotations(ticker);
		
		return analyzeService.analyzeStockQuotations(quotations);
	}
}
