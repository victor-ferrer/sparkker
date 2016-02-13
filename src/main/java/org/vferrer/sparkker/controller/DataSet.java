package org.vferrer.sparkker.controller;

import java.util.List;

public class DataSet 
{
	private String label;
	
	private List<Double> data;

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
