package org.vferrer.sparkker.service.indicators;

import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import org.vferrer.sparkker.stokker.Indicator;
import org.vferrer.sparkker.stokker.StockQuotationJPA;

public class SMAIndicator extends Indicator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6638338338673857439L;

	@Override
	public Indicator calculate(List<StockQuotationJPA> stockList) {
		Double result = stockList.stream().collect(Collectors.summingDouble(new ToDoubleFunction<StockQuotationJPA>() {

			@Override
			public double applyAsDouble(StockQuotationJPA value) {
				return value.getValue();
			}
		}));
		
		result = result / stockList.size();
		this.setValue(result);
		
		return this;

	}

}
