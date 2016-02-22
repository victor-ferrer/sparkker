package org.vferrer.sparkker.service.facts;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TradingSession 
{
	private List<Operation> operationList;
	
	public TradingSession(){
		operationList = new LinkedList<>();
	}
	
	public void addOperation(String type, Date date, Double price, Double score)
	{
		operationList.add(new Operation(type, date, price, score));
	}

	public boolean isOnMarket() {
		
		if (operationList.isEmpty()){
			return false;
		}
		
		return operationList.get(operationList.size() -1).getType().equals("BUY");
	}
	
	public Double getOpeningPrice()
	{
		if (operationList.isEmpty()){
			// FIXME what should we do here
			return 0d;
		}
		
		return operationList.get(operationList.size() -1).getPrice();
	}
	
	

	public List<Operation> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<Operation> operationList) {
		this.operationList = operationList;
	}
}
