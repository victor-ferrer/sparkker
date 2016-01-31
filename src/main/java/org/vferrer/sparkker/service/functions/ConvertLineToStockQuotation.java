package org.vferrer.sparkker.service.functions;

import java.util.Arrays;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.vferrer.sparkker.stokker.StockQuotation;

public class ConvertLineToStockQuotation implements FlatMapFunction<String, StockQuotation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1767503988431271439L;

	@Override
	public Iterable<StockQuotation> call(String line) throws Exception {
		return Arrays.asList(StockQuotation.fromLine(line));
	}

}
