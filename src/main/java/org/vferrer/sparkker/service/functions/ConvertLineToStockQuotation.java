package org.vferrer.sparkker.service.functions;

import java.util.Arrays;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.vferrer.sparkker.stokker.StockQuotationJPA;

public class ConvertLineToStockQuotation implements FlatMapFunction<String, StockQuotationJPA> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1767503988431271439L;

	@Override
	public Iterable<StockQuotationJPA> call(String line) throws Exception {
		return Arrays.asList(StockQuotationJPA.fromLine(line));
	}

}
