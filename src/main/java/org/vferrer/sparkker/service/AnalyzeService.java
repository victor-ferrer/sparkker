package org.vferrer.sparkker.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.rdd.RDDFunctions;
import org.apache.spark.rdd.RDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vferrer.sparkker.service.functions.IndicatorFunction;
import org.vferrer.sparkker.service.functions.SortByDateFunction;
import org.vferrer.sparkker.stokker.AnalizedStockQuotation;
import org.vferrer.sparkker.stokker.Indicator;
import org.vferrer.sparkker.stokker.StockQuotationJPA;

import scala.reflect.ClassTag;

@Service
public class AnalyzeService 
{
	@Autowired
	private JavaSparkContext  sc;
	
	/**
	 * Applies the given indicators to the given stock quotation list
	 * @param stocks
	 * @param indicators
	 * @return
	 * @throws IOException
	 */
	public List<AnalizedStockQuotation> analyzeStockQuotations(List<StockQuotationJPA> stocks, Set<Indicator> indicators) throws IOException
	{
		JavaRDD<StockQuotationJPA> quotationsRDD = sc.parallelize(stocks);
		
		// Sort the records ascending
		JavaRDD<StockQuotationJPA> sortedQuotationsRDD = quotationsRDD.sortBy(new SortByDateFunction(),false,1);
		sortedQuotationsRDD.cache();
		
		// Instantiate the RDDFunctions object
		ClassTag<StockQuotationJPA> classTag = scala.reflect.ClassManifestFactory.fromClass(StockQuotationJPA.class);
		RDD<StockQuotationJPA> rdd = JavaRDD.toRDD(quotationsRDD);
		RDDFunctions<StockQuotationJPA> rddFs = RDDFunctions.fromRDD(rdd, classTag);
		
		// Calculate all indicators in the current time window
		JavaRDD<AnalizedStockQuotation> processedQuotationsRDD = rddFs.sliding(200).toJavaRDD().map(new IndicatorFunction(indicators));
		
		return processedQuotationsRDD.collect();
		
	}
	
}
