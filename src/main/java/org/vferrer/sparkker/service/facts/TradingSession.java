package org.vferrer.sparkker.service.facts;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TradingSession 
{
	private boolean isOnMarket;

	private List<Operation> operationList;
	
	public TradingSession(){
		isOnMarket = false;
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

	public List<Operation> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<Operation> operationList) {
		this.operationList = operationList;
	}
}
