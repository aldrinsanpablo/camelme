package com.accenture.product_management;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultExchange;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.accenture.entities.tables.daos.CategoryDao;
import com.accenture.entities.tables.daos.ProductDao;
import com.accenture.product_management.util.CustomResponseType;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class PersistentContextDI {

	
	@Autowired
	private DSLContext dsl;
	

	@Autowired
	private CamelContext camelContext;
	
	@Bean
	public CategoryDao categoryConnectionProvider() {
	    return new CategoryDao(dsl.configuration());
	}
	
	@Bean
	public ProductDao productConnectionProvider() {
	    return new ProductDao(dsl.configuration());
	}
	
	@Bean
	public CustomResponseType createInstance() {
		return new CustomResponseType();
	}
	
	
	
	
	//Parser
	@Bean
	public ObjectMapper createObjectMapperContext() {
	    return new ObjectMapper();
	}
	

	
	/*@Bean
	public CamelContext createCamelContext() {
	    return new DefaultCamelContext();
	}*/
	/* @Bean
	    public HystrixEventStreamServlet hystrixServlet() {
	        return new HystrixEventStreamServlet();
	    }

	    @Bean
	    public ServletRegistrationBean servletRegistrationBean() {
	        return new ServletRegistrationBean(new HystrixEventStreamServlet(), "/hystrix.stream");
	    }*/
}
