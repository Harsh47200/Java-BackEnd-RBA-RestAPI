package com.SpringSecurityAuthantication.UserAndAdminAuthantication.serviceimpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.Product;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.repository.ProductRepository;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.service.ProductService;


//@Service class as a Spring service for automatic detection and registration.
@Service
public class ProductServiceImpl implements ProductService {

	// Injects an instance of ProductRepository for database operations
	@Autowired
	ProductRepository productRepository;

	@Override
	public Product addItemSave(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	

	@Override
	public String deleteItemById(long productId) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(productId).get();
		productRepository.delete(product);
		return product.getName() + " deleted successfully.";
	}

	@Override
	public Product getUpadteItemById(long productId) {
		// TODO Auto-generated method stub
		return productRepository.findById(productId).get();
	}



	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	


}
