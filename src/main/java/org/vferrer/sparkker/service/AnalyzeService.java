package org.vferrer.sparkker.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.rdd.RDDFunctions;
import org.apache.spark.rdd.RDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vferrer.sparkker.service.functions.ConvertLineToStockQuotation;
import org.vferrer.sparkker.stokker.StockQuotation;

import scala.Tuple2;
import scala.reflect.ClassTag;

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
	
	/**
	 * FIXME It just calculates the moving average per date of a stock stored in text file 
	 */
	public List<Double> analyzeStockQuotations(int slidingWindow) throws IOException
	{
		// Read the sample file
		JavaRDD<String> linesRDD = sc.textFile("C://development//dump.txt");
		
		// Convert the lines to our business objects
		JavaRDD<StockQuotation> quotationsRDD = linesRDD.flatMap(new ConvertLineToStockQuotation());

		// Instantiate the RDDFunctions object
		ClassTag<StockQuotation> classTag = scala.reflect.ClassManifestFactory.fromClass(StockQuotation.class);
		RDD<StockQuotation> rdd = JavaRDD.toRDD(quotationsRDD);
		RDDFunctions<StockQuotation> rddFs = RDDFunctions.fromRDD(rdd, classTag);
		
		// This just applies the sliding function and return the values (to be deleted)
		List<Double> movingAvGValues = rddFs.sliding(slidingWindow).toJavaRDD().map(new MovingAvgFunction()).collect();

		// This applies the sliding function and return the (SMA, DATE) tuple
		JavaPairRDD<Date, Double> smaPerDate = rddFs.sliding(slidingWindow).toJavaRDD().mapToPair(new MovingAvgByDateFunction());
		List<Tuple2<Date, Double>> smaPerDateList = smaPerDate.collect();
		
		// Debug Printing
		for (Tuple2<Date, Double> tuple2 : smaPerDateList) {
			StockQuotation stock = new StockQuotation();
			stock.setStock("MAP.MC");
			stock.setTimestamp(tuple2._1);
			stock.setValue(tuple2._2);
			System.out.println(StockQuotation.toLine(stock));
		}
		
		return movingAvGValues;
		
	}
	
}
