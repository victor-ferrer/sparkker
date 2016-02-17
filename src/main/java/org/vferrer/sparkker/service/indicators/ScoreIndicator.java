package org.vferrer.sparkker.service.indicators;

import java.util.List;

import org.vferrer.sparkker.stokker.Indicator;
import org.vferrer.sparkker.stokker.StockQuotationJPA;

// TODO complete
public class ScoreIndicator extends Indicator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1474074188493733169L;

	@Override
	public Indicator calculate(List<StockQuotationJPA> stockList) {
		this.setValue(0d);
		return this;
	}

}
