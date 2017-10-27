package com.accenture.product_management;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ClientRoute extends RouteBuilder {

	@Value("${detail.fallback.response}")
	String fallbackResponse;
	
	@Value("${warehouse.route.detail}")
	String wareHouseRouteDetail;
	
	@Value("${warehouse.route.placeorder}")
	String wareHouseRoutePlaceOrder;
	
	@Value("${warehouse.route.cancelorder}")
	String wareHouseRouteCancelOrder;
	
	@Value("${warehouse.route.orderlist}")
	String wareHouseRouteOrderList;
	
    @Override
    public void configure() {
    	
    	System.out.println(fallbackResponse+"  "+wareHouseRouteDetail);
    	
    	//retrieve product detail
    	from("direct:detail")
    	.log(" Client request: ${body}")
    	.hystrix()
        	.hystrixConfiguration()
             .executionTimeoutInMilliseconds(5000).circuitBreakerSleepWindowInMilliseconds(10000)
             .end()
    	.setHeader(Exchange.HTTP_METHOD,constant(org.springframework.http.HttpMethod.GET))
    	.setHeader(Exchange.HTTP_QUERY, simple("productId=${in.headers.productId}"))
    	  .to(wareHouseRouteDetail)//default "http://123.0.0.1:8080/warehouse/getProductDetails"
    	  .onFallback()
        // use fallback if there was an exception or timeout
        	.log("Hystrix fallback start: ${threadName}")
        	.transform().constant(fallbackResponse)//"Item price and stock not yet available"
        	.log("Hystrix fallback end: ${threadName}")
        .end();
    	
    	
    	//place order
    	from("direct:placeOrder")
    	.log(" Client request: ${body}")
    	.hystrix()
        	.hystrixConfiguration()
             .executionTimeoutInMilliseconds(5000).circuitBreakerSleepWindowInMilliseconds(10000)
             .end()
    	.setHeader(Exchange.HTTP_METHOD,constant(org.springframework.http.HttpMethod.GET))
    	.setHeader(Exchange.HTTP_QUERY, simple("productId=${in.headers.productId}"))
    	  .to(wareHouseRoutePlaceOrder)//default "http://123.0.0.1:8080/warehouse/getProductDetails"
    	  .onFallback()
        // use fallback if there was an exception or timeout
        	.log("Hystrix fallback start: ${threadName}")
        	.transform().constant(fallbackResponse)//"Item price and stock not yet available"
        	.log("Hystrix fallback end: ${threadName}")
        .end();
    	
    	
    	//cancel order
    	from("direct:cancelOrder")
    	.log(" Client request: ${body}")
    	.hystrix()
        	.hystrixConfiguration()
             .executionTimeoutInMilliseconds(5000).circuitBreakerSleepWindowInMilliseconds(10000)
             .end()
    	.setHeader(Exchange.HTTP_METHOD,constant(org.springframework.http.HttpMethod.GET))
    	.setHeader(Exchange.HTTP_QUERY, simple("productId=${in.headers.orderId}"))
    	  .to(wareHouseRouteCancelOrder)//default "http://123.0.0.1:8080/warehouse/getProductDetails"
    	  .onFallback()
        // use fallback if there was an exception or timeout
        	.log("Hystrix fallback start: ${threadName}")
        	.transform().constant(fallbackResponse)//"Item price and stock not yet available"
        	.log("Hystrix fallback end: ${threadName}")
        .end();
    	
    	
    	//list of orders
    	from("direct:listOrder")
    	.log(" Client request: ${body}")
    	.hystrix()
        	.hystrixConfiguration()
             .executionTimeoutInMilliseconds(5000).circuitBreakerSleepWindowInMilliseconds(10000)
             .end()
    	.setHeader(Exchange.HTTP_METHOD,constant(org.springframework.http.HttpMethod.GET))
    	//.setHeader(Exchange.HTTP_QUERY, simple("productId=${in.headers.orderId}"))
    	  .to(wareHouseRouteOrderList)//default "http://123.0.0.1:8080/warehouse/getProductDetails"
    	  .onFallback()
        // use fallback if there was an exception or timeout
        	.log("Hystrix fallback start: ${threadName}")
        	.transform().constant(fallbackResponse)//"Item price and stock not yet available"
        	.log("Hystrix fallback end: ${threadName}")
        .end();
    	
    	
     }
    	

}