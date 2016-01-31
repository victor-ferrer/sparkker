package org.vferrer.sparkker.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import org.apache.spark.api.java.function.Function;
import org.vferrer.sparkker.stokker.StockQuotation;

public class MovingAvgFunction implements Function<Object, Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1017865068496467920L;

	@Override
	public Double call(Object v1) throws Exception {
		
		StockQuotation[] stocks = (StockQuotation[]) v1;
		List<StockQuotation> stockList = Arrays.asList(stocks);
		
		Double result = stockList.stream().collect(Collectors.summingDouble(new ToDoubleFunction<StockQuotation>() {

			@Override
			public double applyAsDouble(StockQuotation value) {
				return value.getValue();
			}
		}));
		
		
		return result / stockList.size();

	}
}
