package com.fedex.oce.service;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fedex.oce.entity.CurrencyConversionBean;

//@FeignClient(name = "CURRENCY-EXCHANGE-SERVICE") //, url = "localhost:8000")
//@RibbonClient(name="CURRENCY-EXCHANGE-SERVICE")
@FeignClient(name="zuul-api-gateway-server")
public interface CurrencyConvertorServiceProxy {
	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	CurrencyConversionBean getExchangeValue(@PathVariable String from, @PathVariable String to);
}
