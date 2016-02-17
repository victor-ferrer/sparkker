package org.vferrer.sparkker.service.facts;

import java.util.List;

import org.vferrer.sparkker.stokker.AnalizedStockQuotation;

import com.clearspring.analytics.util.Preconditions;

/**
 * This class will hold the information regarding one stock for one set of rule execution
 * @author efevict
 *
 */
public class StockRuleData 
{
	private String ticker;
	private List<AnalizedStockQuotation> quotations;

	
	public StockRuleData(String ticker, List<AnalizedStockQuotation> quotations)
	{
		this.ticker = ticker;
		this.quotations = quotations;
	}
	
	public AnalizedStockQuotation getAt(int reverseIndex)
	{
		Preconditions.checkArgument(reverseIndex < quotations.size(),"Invalid reverse index " + reverseIndex + " size " + quotations.size());
		return quotations.get(quotations.size() - reverseIndex);
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
}
