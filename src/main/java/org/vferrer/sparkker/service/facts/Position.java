package org.vferrer.sparkker.service.facts;

import java.io.Serializable;

import com.clearspring.analytics.util.Preconditions;

public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3364334465263607335L;


	enum Status {
		NEW, OPEN, CLOSED
	}
	
	enum Mode {
		LONG, SHORT
	}
	
	private String stock;
	
	private Operation openingOperation;
	
	private Operation closingOperation;

	private Status status;
	
	private Mode mode;
	
	
	public Position(Mode mode){
		Preconditions.checkArgument(mode == Mode.LONG, "Only LONG positions are supported");
		
		this.status = Status.NEW;
		this.mode = mode;
	}
	
	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Operation getOpeningOperation() {
		return openingOperation;
	}
	
	public Double getYield()
	{
		Preconditions.checkState(status == Status.CLOSED, "Yield can be calculated only in CLOSED positions");

		
		if (mode == Mode.LONG){
			Double startPrice = getOpeningOperation().getPrice();
			Double endPrice = getClosingOperation().getPrice();
			
			return 100d * ((endPrice / startPrice) - 1d);
		}
		else {
			return 0d;
		}
		
	}
	

	public void open(Operation openingOperation) {
		this.openingOperation = openingOperation;
		this.status = Status.OPEN;
	}

	public Operation getClosingOperation() {
		return closingOperation;
	}

	public void close(Operation closingOperation) {
		this.closingOperation = closingOperation;
		this.status = Status.CLOSED;
	}

	public Status getStatus() {
		return status;
	}


	public Mode getMode() {
		return mode;
	}
	
}
