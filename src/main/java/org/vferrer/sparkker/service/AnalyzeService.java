package org.vferrer.sparkker.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.rdd.RDDFunctions;
import org.apache.spark.rdd.RDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vferrer.sparkker.service.functions.MovingAvgByDateFunction;
import org.vferrer.sparkker.stokker.StockQuotation;

import scala.Tuple2;
import scala.reflect.ClassTag;

@Service
public class AnalyzeService 
{
	@Autowired
	private JavaSparkContext  sc;
	
	/**
	 * FIXME It just calculates the moving average of a stock list given as input
	 */
	public List<StockQuotation> analyzeStockQuotations(List<StockQuotation> stocks, int slidingWindow) throws IOException
	{
		JavaRDD<StockQuotation> quotationsRDD = sc.parallelize(stocks);

		// Instantiate the RDDFunctions object
		ClassTag<StockQuotation> classTag = scala.reflect.ClassManifestFactory.fromClass(StockQuotation.class);
		RDD<StockQuotation> rdd = JavaRDD.toRDD(quotationsRDD);
		RDDFunctions<StockQuotation> rddFs = RDDFunctions.fromRDD(rdd, classTag);
		
		// This applies the sliding function and return the (DATE,SMA) tuple
		JavaPairRDD<Date, Double> smaPerDate = rddFs.sliding(slidingWindow).toJavaRDD().mapToPair(new MovingAvgByDateFunction());
		List<Tuple2<Date, Double>> smaPerDateList = smaPerDate.collect();
		
		// Debug Printing
		List<StockQuotation> toReturn = new ArrayList<>();
		for (Tuple2<Date, Double> tuple2 : smaPerDateList) {
			StockQuotation stock = new StockQuotation();
			stock.setStock(stocks.get(0).getStock());
			stock.setTimestamp(tuple2._1);
			stock.setValue(tuple2._2);
			toReturn.add(stock);
			System.out.println(StockQuotation.toLine(stock));
		}
		
		return toReturn;
		
	}
	
}
