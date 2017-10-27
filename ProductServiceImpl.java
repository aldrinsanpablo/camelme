package com.accenture.product_management.services;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.entities.tables.pojos.Product;
import com.accenture.entities.tables.pojos.ProductDetail;
import com.accenture.product_management.respositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private ProducerTemplate producerTemplate;

	@Autowired
	ObjectMapper objectMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.accenture.product_management.services.ProductService#create(com.accenture
	 * .entities.tables.pojos.Product)
	 */
	@Override
	public boolean create(Product product) {
		return productRepository.create(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.accenture.product_management.services.ProductService#update(com.accenture
	 * .entities.tables.pojos.Product)
	 */
	@Override
	public boolean update(Product product) {
		return productRepository.update(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.accenture.product_management.services.ProductService#retrive(int)
	 */
	@Override
	public Product retrieve(long productId) {
		return productRepository.retrieve(productId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.accenture.product_management.services.ProductService#delete(com.accenture
	 * .entities.tables.pojos.Product)
	 */
	@Override
	public boolean delete(Product product) {
		return productRepository.delete(product);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.accenture.product_management.services.ProductService#getAll()
	 */
	@Override
	public List<Product> getAll() {
		return productRepository.getAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.accenture.product_management.services.ProductService#getByCategory(int)
	 */
	@Override
	public List<Product> getByCategory(int categoryId) {
		return productRepository.getByCategory(categoryId);
	}

	@Override
	public ProductDetail retrieveProductDetail(long productId) {
		Object result = producerTemplate.requestBodyAndHeader("direct:detail", null, "productId",
				Long.toString(productId), String.class);
		System.out.println("Response:" + result);
		ProductDetail det = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			det = objectMapper.readValue(result.toString(), ProductDetail.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(">>>>>>>>>>>>Error<<<<<<<<<. Either connection time out or invalid response from warehous");
			//e.printStackTrace();
		}
		return det;
	}

}
