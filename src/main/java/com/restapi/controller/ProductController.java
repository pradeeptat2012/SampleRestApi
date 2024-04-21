package com.restapi.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.entity.Product;
import com.restapi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
	private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	
	private final ProductService productService;
	
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
	
	 /**
     * Create a new product.
     *
     * @param product the product to create
     * @return the ResponseEntity with status 200 (OK) and with body of the new product
     */
    @PostMapping("/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
    	LOGGER.info("Inside saveProduct()method of ProductController!");
        Product newProduct = productService.saveProduct(product);
        return ResponseEntity.ok(newProduct);
    }
    
    /**
     * Get all products.
     *
     * @return the ResponseEntity with status 200 (OK) and with body of the list of products
     */
    @GetMapping("/products")
    public List<Product> getAllProducts() {
    	LOGGER.info("Inside getAllProducts()method of ProductController!");
        return productService.getAllProducts();
    }
    
    /**
     * Get a product by ID.
     *
     * @param id the ID of the product to get
     * @return the ResponseEntity with status 200 (OK) and with body of the product, or with status 404 (Not Found) if the product does not exist
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    	LOGGER.info("Inside getProductById()method of ProductController!");
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Update a product by ID.
     *
     * @param id the ID of the product to update
     * @param product the updated product
     * @return the ResponseEntity with status 200 (OK) and with body of the updated product, or with status 404 (Not Found) if the product does not exist
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
    	LOGGER.info("Inside updateProduct()method of ProductController!");
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }
    
    /**
     * Delete a product by ID.
     *
     * @param id the ID of the product to delete
     * @return the ResponseEntity with status 200 (OK) and with body of the message "Product deleted successfully"
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
    	LOGGER.info("Inside deleteProduct()method of ProductController!");
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
