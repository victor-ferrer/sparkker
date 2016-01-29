package org.vferrer.sparkker.service;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vferrer.sparkker.stokker.StockQuotation;

@Service
public class AnalyzeService 
{
	@Autowired
	private JavaSparkContext  sc;
	
	
	// TODO We should replace this by a real implementation returning a complex object with
	// several values
	public Long analyzeStockQuotations(List<StockQuotation> stockList)
	{
		JavaRDD<StockQuotation> stocksRDD = sc.parallelize(stockList);
		
		return stocksRDD.count();
	}
	
}
