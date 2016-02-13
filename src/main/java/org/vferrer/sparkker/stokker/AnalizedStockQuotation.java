package org.vferrer.sparkker.stokker;

import java.util.HashMap;
import java.util.Map;

public class AnalizedStockQuotation extends StockQuotationJPA {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8319429476584338764L;

	
	private Map<String,Indicator> indicators;

	
	public AnalizedStockQuotation(StockQuotationJPA stockQuotation){
		super();
		indicators = new HashMap<>();
		
		setStock(stockQuotation.getStock());
		setTimestamp(stockQuotation.getTimestamp());
		setValue(stockQuotation.getValue());
	}


	public Map<String, Indicator> getIndicators() {
		return indicators;
	}
}
