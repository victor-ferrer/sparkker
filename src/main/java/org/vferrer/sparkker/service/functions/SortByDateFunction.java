package org.vferrer.sparkker.service.functions;

import org.apache.spark.api.java.function.Function;
import org.vferrer.sparkker.stokker.StockQuotationJPA;

public class SortByDateFunction implements Function<StockQuotationJPA,Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817563065127046279L;

	@Override
	public Long call(StockQuotationJPA v1) throws Exception {
		return v1.getTimestamp().getTime();
	}


}
