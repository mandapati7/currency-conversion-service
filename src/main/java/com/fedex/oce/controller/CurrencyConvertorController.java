package com.fedex.oce.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fedex.oce.entity.CurrencyConversionBean;
import com.fedex.oce.service.CurrencyConvertorServiceProxy;

@RestController
public class CurrencyConvertorController {
	
	private final static Logger LOG = LoggerFactory.getLogger(CurrencyConvertorController.class);


	@Autowired(required=true)
	CurrencyConvertorServiceProxy ccProxy;

	@GetMapping("/ping")
	public String ping() {
		return "Ping Succcess";
	}

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrey(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		final String URI = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";

		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversionBean> ccBean = new RestTemplate().getForEntity(URI,
				CurrencyConversionBean.class, uriVariables);

		CurrencyConversionBean ccBody = ccBean.getBody();

		BigDecimal currencyValue = ccBody.getConversionMultiple().multiply(quantity);

		return new CurrencyConversionBean(ccBody.getId(), from, to, ccBody.getConversionMultiple(), quantity,
				currencyValue, ccBody.getPort());
	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurreyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean ccBean = ccProxy.getExchangeValue(from, to);
		
		LOG.info("{}", ccBean);

		return new CurrencyConversionBean(ccBean.getId(), from, to, ccBean.getConversionMultiple(), quantity,
				ccBean.getConversionMultiple().multiply(quantity), ccBean.getPort());
	}

}
