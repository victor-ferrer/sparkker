package org.vferrer.sparkker.service.functions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import org.apache.spark.api.java.function.PairFunction;
import org.vferrer.sparkker.stokker.StockQuotation;

import scala.Tuple2;

public class MovingAvgByDateFunction implements PairFunction<Object,Date,Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9220435667459839141L;

	@Override
	public Tuple2<Date, Double> call(Object t) throws Exception {
		
		StockQuotation[] stocks = (StockQuotation[]) t;
		List<StockQuotation> stockList = Arrays.asList(stocks);
		
		Double result = stockList.stream().collect(Collectors.summingDouble(new ToDoubleFunction<StockQuotation>() {

			@Override
			public double applyAsDouble(StockQuotation value) {
				return value.getValue();
			}
		}));
		
		result = result / stockList.size();

		return new Tuple2<Date, Double>(stockList.get(0).getTimestamp(),result);
	}
}
