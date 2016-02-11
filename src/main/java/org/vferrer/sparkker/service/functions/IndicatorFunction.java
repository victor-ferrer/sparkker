package org.vferrer.sparkker.service.functions;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.spark.api.java.function.Function;
import org.vferrer.sparkker.stokker.AnalizedStockQuotation;
import org.vferrer.sparkker.stokker.Indicator;
import org.vferrer.sparkker.stokker.StockQuotationJPA;

public class IndicatorFunction implements Function<Object,AnalizedStockQuotation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4341178669477749449L;
	
	private Set<Indicator> indicators;

	public IndicatorFunction(Set<Indicator> indicators2) 
	{
		this.indicators = indicators2;
	}
	
	@Override
	public AnalizedStockQuotation call(Object arg0) throws Exception {

		StockQuotationJPA[] stocks = (StockQuotationJPA[]) arg0;
		List<StockQuotationJPA> stockList = Arrays.asList(stocks);
		
		AnalizedStockQuotation toReturn = new AnalizedStockQuotation(stockList.get(0));
		
		indicators.forEach(indicator -> toReturn.getIndicators().add(indicator.clone().calculate(stockList)) );
		
		return toReturn;
	}
}
