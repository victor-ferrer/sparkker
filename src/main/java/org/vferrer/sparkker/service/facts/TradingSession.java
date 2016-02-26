package org.vferrer.sparkker.service.facts;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.vferrer.sparkker.service.facts.Position.Mode;
import org.vferrer.sparkker.service.facts.Position.Status;
import org.vferrer.sparkker.stokker.AnalizedStockQuotation;
import org.vferrer.sparkker.stokker.Indicator;

import com.google.common.base.Preconditions;

public class TradingSession 
{
	private LinkedList<Position> positionList;
	private LinkedList<AnalizedStockQuotation> quotationList;
	
	public TradingSession(){
		positionList = new LinkedList<>();
		quotationList = new LinkedList<>();
	}
	
	
	public void openPosition(String stock, Date date, Double price, Double score)
	{
		Position position = new Position(Mode.LONG);
		position.setStock(stock);
		positionList.addLast(position);
		
		Operation openiningOperation = new Operation("BUY", date, price, score);
		
		
		openiningOperation.setSmaInclination(getSMA200Steepness(200));
		
		positionList.getLast().open(openiningOperation );
	}
	
	public void closePosition(Date date, Double price, Double score)
	{
		Preconditions.checkArgument(positionList.getLast().getStatus() == Status.OPEN);
		
		Operation closingOperation = new Operation("SELL", date, price, score);
		
		closingOperation.setSmaInclination(getSMA200Steepness(200));
		
		positionList.getLast().close(closingOperation );
	}
	
	public boolean isOnMarket() {
		
		if (positionList.isEmpty()){
			return false;
		}
		
		return positionList.getLast().getStatus() == Status.OPEN;
	}
	
	public Double getOpeningPrice()
	{
		if (positionList.isEmpty()){
			// FIXME what should we do here
			return 0d;
		}
		
		return positionList.getLast().getOpeningOperation().getPrice();
	}
	
	public List<Position> getPositionList() {
		return positionList;
	}

	public void processStatistics(AnalizedStockQuotation quotation)
	{
		System.out.println(String.format("Stats...SMA[%s] - SCORE [%s] - Price [%s]", quotation.getIndicator("SMA").getValue().toString(),
																					  quotation.getIndicator("SCORE").getValue().toString(),
																					  quotation.getValue().toString()));
		
		// We store store the quotations so analysis can be perform later on the indicators
		quotationList.add(quotation);
		// TODO Store the previous scores
		
	}
	
	/**
	 * 
	 * @param measurementsLenght
	 * @return
	 */
	public Double getSMA200Steepness(int measurementsLenght) {
		
		// TODO Recover the number of measurements from the end of the Quotations list
		LinkedList<AnalizedStockQuotation> quotations = getLastAnalizedQuotations(measurementsLenght);
		
		if (!quotations.isEmpty()){
			Double num = quotations.getLast().getIndicator("SMA").getValue() - quotations.getFirst().getIndicator("SMA").getValue();
			// m = (y2 - y1 / (x2 - x1) = (ending indicator value - starting indicator value) / (ending day number - starting day number)  
			Double result = num / quotations.size();
			
			return result;
		}
		else {
			System.out.println("No quotations for calculating the SMA inclination");
			return 0d;
		}
	}
	
	public LinkedList<AnalizedStockQuotation> getLastAnalizedQuotations(int measurementCount){
		Iterator<AnalizedStockQuotation> it = quotationList.descendingIterator();
		
		int count = 0;
		
		LinkedList<AnalizedStockQuotation> result = new LinkedList<>();
		while (it.hasNext() && count < measurementCount)
		{
			result.add(it.next());
			count++;
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param measurementsLenght
	 * @return
	 */
	public List<Double> getIndicatorValues(Indicator indicator, int measurementsLenght) {
		
		// TODO Recover the number of measurements from the end of the Quotations list
		
		// TODO  
		
		
		return Arrays.asList(0d);
	}
	
	
	
}
