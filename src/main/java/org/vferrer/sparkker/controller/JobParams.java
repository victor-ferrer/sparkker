package org.vferrer.sparkker.controller;

import java.io.Serializable;

public class JobParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6676625484645631758L;

	private String targetStock;
	
	private String stopLossPerc;
	
	private String takeProfitPerc;

	public String getTargetStock() {
		return targetStock;
	}

	public void setTargetStock(String targetStock) {
		this.targetStock = targetStock;
	}

	public String getStopLossPerc() {
		return stopLossPerc;
	}

	public void setStopLossPerc(String stopLossPerc) {
		this.stopLossPerc = stopLossPerc;
	}

	public String getTakeProfitPerc() {
		return takeProfitPerc;
	}

	public void setTakeProfitPerc(String takeProfitPerc) {
		this.takeProfitPerc = takeProfitPerc;
	}
	
	
}
