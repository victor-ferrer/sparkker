package org.vferrer.sparkker.stokker;

import java.util.Date;

/**
 * Simple POJO class holding the values returned by Stokker for a Stock Quotation
 * @author efevict
 *
 */
public class StockQuotation 
{
	
	private String stock;
	
	private Double value;
	
	private Date timestamp;
	
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	

}
