package org.vferrer.sparkker.stokker;

import java.util.HashSet;
import java.util.Set;

public class AnalizedStockQuotation extends StockQuotationJPA {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8319429476584338764L;

	
	private Set<Indicator> indicators;

	
	public AnalizedStockQuotation(StockQuotationJPA stockQuotation){
		super();
		indicators = new HashSet<>();
		
		setStock(stockQuotation.getStock());
		setTimestamp(stockQuotation.getTimestamp());
		setValue(stockQuotation.getValue());
	}


	public Set<Indicator> getIndicators() {
		return indicators;
	}
}
