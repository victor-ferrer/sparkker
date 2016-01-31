package org.vferrer.sparkker.service.functions;

import org.apache.spark.api.java.function.PairFunction;
import org.vferrer.sparkker.stokker.StockQuotation;

import scala.Tuple2;

public class StockDiscriminator implements PairFunction<StockQuotation,String,StockQuotation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5422573623632090169L;

	@Override
	public Tuple2<String, StockQuotation> call(StockQuotation stock) throws Exception {
		return new Tuple2<String, StockQuotation>(stock.getStock(), stock);
	}

}
