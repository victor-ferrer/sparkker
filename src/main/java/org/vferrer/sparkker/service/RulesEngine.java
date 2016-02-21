package org.vferrer.sparkker.service;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vferrer.sparkker.service.facts.Operation;
import org.vferrer.sparkker.service.facts.TradingSession;
import org.vferrer.sparkker.service.indicators.ScoreIndicator;
import org.vferrer.sparkker.stokker.AnalizedStockQuotation;

/**
 * 
 * @author victor-ferrer
 *
 */
@Service
public class RulesEngine {

	@Autowired
	private KieContainer kieContainer;

	public List<Operation> executeRules(List<AnalizedStockQuotation> quotations) throws Exception {

		// Init session and global variables
		KieSession kSession = kieContainer.newKieSession();

		TradingSession session = new TradingSession();
//		kSession.setGlobal("session", session);
		kSession.insert(session);

		// FIXME Add an empty indicator
		quotations.forEach(quot -> quot.getIndicators().put("SCORE", new ScoreIndicator()));
		
		// Submit all and fire
		for (AnalizedStockQuotation quot : quotations){
			kSession.insert(quot);
			kSession.fireAllRules();
			// Recover results
		}
		List<Operation> toReturn = session.getOperationList();
		
		kSession.dispose();
		
		return toReturn;
	}
}
