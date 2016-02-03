package org.vferrer.sparkker.stokker;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name="stokker")
public interface StokkerClient {

		@RequestMapping(method = RequestMethod.GET, value = "/stockQuotationJPAs/search/findValueByStock")
		Resources<StockQuotation> getAllStockQuotations(@RequestParam("ticker") String stock);
		
		@RequestMapping(method = RequestMethod.GET, value = "/stockQuotationJPAs/search/findTopByStockOrderByTimestampDesc")
		StockQuotation getLastStockPrice(@RequestParam("ticker") String stock);
}
