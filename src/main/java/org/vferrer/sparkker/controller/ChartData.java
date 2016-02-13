package org.vferrer.sparkker.controller;

import java.io.Serializable;
import java.util.List;


public class ChartData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -498236184783450805L;
	
	private List<String> labels;
	
	private List<List<Double>> datasets;
	
	private List<String> series;
	
	public ChartData()
	{
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<List<Double>> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<List<Double>> datasets) {
		this.datasets = datasets;
	}

	public List<String> getSeries() {
		return series;
	}

	public void setSeries(List<String> series) {
		this.series = series;
	}
	
}
