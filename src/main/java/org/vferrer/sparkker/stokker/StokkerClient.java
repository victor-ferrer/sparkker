package org.vferrer.sparkker.stokker;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("stokker")
public interface StokkerClient {

		@RequestMapping(method = RequestMethod.GET, value = "/stockQuotationJPAs/search/findValueByStock")
		List<StockQuotation> getAllStockQuotations(@RequestParam("ticker") String stock);
		
		@RequestMapping(method = RequestMethod.GET, value = "/stockQuotationJPAs/search/findTopByStockOrderByTimestampDesc")
		StockQuotation getLastStockPrice(@RequestParam("ticker") String stock);

	
}
