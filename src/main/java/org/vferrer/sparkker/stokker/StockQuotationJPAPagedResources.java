package org.vferrer.sparkker.stokker;

import java.util.Collection;
import java.util.Collections;

import org.springframework.hateoas.PagedResources;

public class StockQuotationJPAPagedResources extends PagedResources<StockQuotationJPA> {

	@SuppressWarnings("unchecked")
	public StockQuotationJPAPagedResources(final Collection<StockQuotationJPA> content, final PageMetadata metadata) {
		super(content, metadata, Collections.emptyList());
	}

	public StockQuotationJPAPagedResources() {
		super();
	}
}