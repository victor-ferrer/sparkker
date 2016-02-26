package org.vferrer.sparkker.service.facts;

import java.util.Date;

public class Operation {

	private String type;
	private Date date;
	private Double price;
	private Double score;
	private Double smaInclination;
	
	
	public Operation(String type, Date date, Double price, Double score) {
		this.type = type;
		this.date = date;
		this.price = price;
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getSmaInclination() {
		return smaInclination;
	}

	public void setSmaInclination(Double smaInclination) {
		this.smaInclination = smaInclination;
	}

}
