package org.vferrer.sparkker.service.indicators;

import org.vferrer.sparkker.stokker.Indicator;
import org.vferrer.sparkker.stokker.Indicator.Granularity;

public class IndicatorsFactory 
{

	/*
	 * Simple Moving Average
	 */
	public static Indicator sma(Granularity granularity, Integer windowLength) 
	{
		Indicator toReturn = new SMAIndicator();
		toReturn.setName("SMA");
		toReturn.setGranularity(granularity);
		toReturn.setGranularity(granularity);
		toReturn.setWindowLength(windowLength);
		
		return toReturn;
	}
	
	
	/*
	 * MAX
	 */
	public static Indicator max(Granularity granularity, Integer windowLength) 
	{
		Indicator toReturn = new MAXIndicator();
		toReturn.setName("MAX");
		toReturn.setGranularity(granularity);
		toReturn.setGranularity(granularity);
		toReturn.setWindowLength(windowLength);
		
		return toReturn;
	}
	
}
