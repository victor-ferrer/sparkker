package org.vferrer.sparkker.service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.rdd.RDDFunctions;
import org.apache.spark.rdd.RDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vferrer.sparkker.service.functions.ConvertLineToStockQuotation;
import org.vferrer.sparkker.service.functions.MovingAvgByDateFunction;
import org.vferrer.sparkker.stokker.StockQuotation;

import com.google.common.base.Preconditions;

import scala.Tuple2;
import scala.reflect.ClassTag;

@Service
public class AnalyzeService 
{
	@Autowired
	private JavaSparkContext  sc;
	
	private String filePath;
	
	
	@PostConstruct
	public void init(){
		URL url = AnalyzeService.class.getResource("/sampleData/dump.txt");
		Preconditions.checkNotNull(url, "Sample data file counld not be found in this URL: /sampleData/dump.txt");
		
		filePath = url.getPath();
	}

	
	
	/**
	 * FIXME It just calculates the moving average per date of a stock stored in text file 
	 */
	public List<StockQuotation> analyzeStockQuotations(int slidingWindow) throws IOException
	{
		// Read the sample file
		JavaRDD<String> linesRDD = sc.textFile(filePath);
		
		// Convert the lines to our business objects
		JavaRDD<StockQuotation> quotationsRDD = linesRDD.flatMap(new ConvertLineToStockQuotation());

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
			stock.setStock("MAP.MC");
			stock.setTimestamp(tuple2._1);
			stock.setValue(tuple2._2);
			toReturn.add(stock);
			System.out.println(StockQuotation.toLine(stock));
		}
		
		return toReturn;
		
	}
	
}
