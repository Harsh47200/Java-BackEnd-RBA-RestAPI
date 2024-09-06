package com.SpringSecurityAuthantication.UserAndAdminAuthantication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.Product;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.response.ApiResponseProduct;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.serviceimpl.ProductServiceImpl;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl serviceImpl;

    @PostMapping("/addproduct")
    public ResponseEntity<ApiResponseProduct<Product>> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = serviceImpl.addItemSave(product);
            return new ResponseEntity<>(new ApiResponseProduct<>(201, "Product added successfully!", savedProduct), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseProduct<>(500, "Error adding product: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allProducts")
    public ResponseEntity<ApiResponseProduct<List<Product>>> getAllProducts() {
        try {
            List<Product> products = serviceImpl.getAllProducts();
            System.out.println("products+++++++++list" + products);
            return new ResponseEntity<>(new ApiResponseProduct<>(200, "Products fetched successfully!", products), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseProduct<>(500, "Error fetching products: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseProduct<Void>> deleteProductById(@PathVariable("id") long id) {
        try {
            serviceImpl.deleteItemById(id);
            return new ResponseEntity<>(new ApiResponseProduct<>(200, "Product deleted successfully!", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseProduct<>(500, "Error deleting product: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
