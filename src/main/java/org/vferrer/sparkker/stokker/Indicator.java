package org.vferrer.sparkker.stokker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;

public abstract class Indicator implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7383425174742805085L;

	public enum Granularity {
		
		DAY("D"), WEEK("W"), MONTH("M");
		
		private Granularity(String label){
			this.label = label;
		}

		private String label;
		
		public String getLabel(){
			return label;
		}
		
	}
	
	private String name;
	
	private Map<String,Integer> parameters;
	
	private Integer windowLength;
	
	private Double value;
	
	private Granularity granularity;

	public Indicator(){
		parameters = new HashMap<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String,Integer> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String,Integer> parameters) {
		this.parameters = parameters;
	}

	public Granularity getGranularity() {
		return granularity;
	}

	public void setGranularity(Granularity granularity) {
		this.granularity = granularity;
	}

	public Double getValue() {
		return value;
	}
	
	public void increaseValue(Double delta){
		if (value != null){
			value = value + delta;
		}
		else {
			value = delta;
		}
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getWindowLength() {
		return windowLength;
	}

	public void setWindowLength(Integer windowLength) {
		this.windowLength = windowLength;
	}
	
	@Override
	public Indicator clone(){
		return (Indicator) SerializationUtils.clone(this);
	}
	
	@Override
	public int hashCode(){
		return java.util.Objects.hash(name,granularity.getLabel(),windowLength);
	}
	
	@Override
	public boolean equals(Object otherIndicator){
		
		if (!(otherIndicator instanceof Indicator)){
			return false;
		}
		
		Indicator other = (Indicator) otherIndicator;
	
		return name.equals(other.getName()) && (granularity == other.granularity) && (windowLength.compareTo(other.getWindowLength()) == 0); 
	}
	
	public abstract Indicator calculate(List<StockQuotationJPA> stockList);		
}
