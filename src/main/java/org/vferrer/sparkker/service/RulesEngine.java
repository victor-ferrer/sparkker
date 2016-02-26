package org.vferrer.sparkker.service;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vferrer.sparkker.controller.JobParams;
import org.vferrer.sparkker.service.facts.Position;
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

	@Autowired
	private JobParams defaultParams;
	
	public List<Position> executeRules(List<AnalizedStockQuotation> quotations, JobParams jobConfig) throws Exception {

		// Init session and global variables
		KieSession kSession = kieContainer.newKieSession();

		if (jobConfig == null){
			jobConfig = defaultParams;
		}
		
		// Placeholder for operations and statistics
		TradingSession session = new TradingSession(jobConfig);
		kSession.insert(session);

		quotations.forEach(quot -> quot.getIndicators().put("SCORE", new ScoreIndicator()));
		
		// Submit each quotation and fire
		for (AnalizedStockQuotation quot : quotations){
			kSession.insert(quot);
			kSession.fireAllRules();
		}
		// Recover results
		List<Position> toReturn = session.getPositionList();
		
		kSession.dispose();
		
		return toReturn;
	}
}
