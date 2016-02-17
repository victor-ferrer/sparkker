package org.vferrer.sparkker.controller;

import java.io.Serializable;
import java.util.List;

/**
 * Convenience POJO class for sending data to the UI
 * @author efevict
 *
 */
public class ChartData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -498236184783450805L;
	
	private List<String> labels;
	
	private List<List<Double>> datasets;
	
	private List<String> series;
	
	private List<String> labelsVoting;
	
	private List<List<Double>> datasetsVoting;
	
	private List<String> seriesVoting;
	
	
	
	
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

	public List<String> getSeriesVoting() {
		return seriesVoting;
	}

	public void setSeriesVoting(List<String> seriesVoting) {
		this.seriesVoting = seriesVoting;
	}

	public List<List<Double>> getDatasetsVoting() {
		return datasetsVoting;
	}

	public void setDatasetsVoting(List<List<Double>> datasetsVoting) {
		this.datasetsVoting = datasetsVoting;
	}

	public List<String> getLabelsVoting() {
		return labelsVoting;
	}

	public void setLabelsVoting(List<String> labelsVoting) {
		this.labelsVoting = labelsVoting;
	}
	
}
