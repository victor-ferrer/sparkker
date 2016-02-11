package org.vferrer.sparkker.stokker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Simple POJO class holding the values returned by Stokker for a Stock Quotation
 * @author efevict
 *
 */
@JsonIgnoreProperties(value={"_links","link"})
public class StockQuotationJPA implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1236069325747069134L;

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
	
	public static StockQuotationJPA fromLine(String line) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS.S");
		
		// REP.MC,9.49,2016-01-29 17:36:00.0
		String[] chunks = line.split(",");
		
		StockQuotationJPA toReturn = new StockQuotationJPA();
		toReturn.setStock(chunks[0]);
		toReturn.setValue(Double.parseDouble(chunks[1]));
		toReturn.setTimestamp(sdf.parse(chunks[2]));
		
		return toReturn;
	}
	
	public static String toLine(StockQuotationJPA stock)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS.S");
		
		return String.format("%s;%s;%s", stock.getStock(), sdf.format(stock.getTimestamp()), stock.getValue().toString());
	}

}
