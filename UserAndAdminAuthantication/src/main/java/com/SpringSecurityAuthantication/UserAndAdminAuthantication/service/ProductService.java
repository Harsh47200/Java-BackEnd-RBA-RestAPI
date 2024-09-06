package com.SpringSecurityAuthantication.UserAndAdminAuthantication.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.Product;





public interface ProductService {
	
	// insert product 
	public Product addItemSave(Product product);
	

	// list and pagination, sorteBy name and description
	public List<Product> getAllProducts();
	
	//delete product
	public String deleteItemById(long productId);
	
	//update product
	public Product getUpadteItemById(long productId);
}
