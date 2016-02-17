package org.vferrer.sparkker.service.facts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradingSession 
{
	private List<String> messageList;

	public TradingSession(){
		messageList = new ArrayList<>();
	}
	
	public void addMessage(String message){
		messageList.add(message);
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
}
