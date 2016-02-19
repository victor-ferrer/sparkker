package org.vferrer.sparkker.service.indicators;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.vferrer.sparkker.stokker.Indicator;
import org.vferrer.sparkker.stokker.StockQuotationJPA;

public class MAXIndicator extends Indicator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2997691817985320632L;

	@Override
	public Indicator calculate(List<StockQuotationJPA> stockList) {
		 Optional<StockQuotationJPA> result = stockList.stream().collect(Collectors.maxBy(new Comparator<StockQuotationJPA>(){

			@Override
			public int compare(StockQuotationJPA o1, StockQuotationJPA o2) {
				
				return o1.getValue().compareTo(o2.getValue());
			}}));
				
	    Double maxValue = result.get().getValue();
		this.setValue(maxValue);

		return this;
	}

}
