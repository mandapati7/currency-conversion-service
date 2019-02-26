package com.fedex.oce.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fedex.oce.entity.CurrencyConversionBean;

@FeignClient(name = "currency-exchange", url = "localhost:8000")
public interface CurrencyConvertorServiceProxy {
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	CurrencyConversionBean getExchangeValue(@PathVariable String from, @PathVariable String to);
}
