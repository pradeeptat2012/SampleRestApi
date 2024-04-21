package com.restapi.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.RestApiKafka.RestApiKafkaApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.entity.Product;
import com.restapi.exception.ProductNotFoundException;
import com.restapi.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@SpringBootTest(classes = RestApiKafkaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
	private static Logger LOGGER = LoggerFactory.getLogger(ProductControllerTest.class);
	private static final String END_POINT_PATH = "/api/v1";
	@Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ProductService service;

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
    public void testAddShouldReturn400BadRequest() throws Exception {
		LOGGER.info("Testing 400 Bad Request for Rest Api");
        Product newProduct = new Product();
        newProduct.setId(1L);
        newProduct.setName("shopping");
        newProduct.setPrice(450);
        newProduct.setQuantity(100);
 
        String requestBody = objectMapper.writeValueAsString(newProduct);
 
        mockMvc.perform(post(END_POINT_PATH+"/product").contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;
    }
	
	@Test
	public void testAddShouldReturn201Created() throws Exception {
		LOGGER.info("Testing 200 Source Creation for Rest Api");
		Product newProduct = new Product();
        newProduct.setId(2L);
        newProduct.setName("fulfilment");
        newProduct.setPrice(750);
        newProduct.setQuantity(10);
	 
	    Mockito.when(service.saveProduct(newProduct)).thenReturn(newProduct);
	 
	    String requestBody = objectMapper.writeValueAsString(newProduct);
	 
	    mockMvc.perform(post(END_POINT_PATH+"/product").contentType("application/json")
	            .content(requestBody))
	            .andExpect(status().isCreated())
	            .andDo(print())
	    ;
	 
	}
	
	@Test
	public void testGetShouldReturn404NotFound() throws Exception {
		LOGGER.info("Testing 404 Resource Not found for Rest Api");
	    Long Id = 123L;
	    String requestURI = END_POINT_PATH + "/products" + Id;
	 
	    Mockito.when(service.getProductById(Id)).thenThrow(ProductNotFoundException.class);
	 
	    mockMvc.perform(get(requestURI))
	        .andExpect(status().isNotFound())
	        .andDo(print());
	}
	@Test
	public void testListShouldReturn204NoContent() throws Exception {
		
		LOGGER.info("Testing 204 No Content Available for Rest Api");
		
		String requestURI = END_POINT_PATH + "/products";
	    Mockito.when(service.getAllProducts()).thenReturn(new ArrayList<>());
	 
	    mockMvc.perform(get(requestURI))
	        .andExpect(status().isNoContent())
	        .andDo(print());
	}
	
	@Test
	public void testListShouldReturn200OK() throws Exception {
		
		LOGGER.info("Testing 200 status code for Getting List of All Products in Rest Api");
		
		String requestURI = END_POINT_PATH + "/products";
		Product product1 = new Product();
		product1.setId(3L);
		product1.setName("fulfilment");
		product1.setPrice(650);
		product1.setQuantity(20);
	 
        Product product2 = new Product();
        product2.setId(4L);
        product2.setName("Pricing");
        product2.setPrice(850);
        product2.setQuantity(50);
	 
	    List<Product> listProduct = List.of(product1, product2);
	 
	    Mockito.when(service.getAllProducts()).thenReturn(listProduct);
	 
	    mockMvc.perform(get(requestURI))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json"))
	        .andExpect(jsonPath("$[0].name", is("fulfilment")))
	        .andExpect(jsonPath("$[1].name", is("Pricing")))
	        .andDo(print());
	}
	
	@Test
	public void testUpdateShouldReturn404NotFound() throws Exception {
		LOGGER.info("Testing testUpdateShouldReturn404NotFound() if resource not available");
	    Long productId = 123L;
	    String requestURI = END_POINT_PATH+"/products" + "/" + productId;
	 
	    Product product = new Product();
        product.setId(7L);
        product.setName("Availability");
        product.setPrice(750);
        product.setQuantity(30);
	 
	    Mockito.when(service.updateProduct(productId,product)).thenThrow(ProductNotFoundException.class);
	 
	    String requestBody = objectMapper.writeValueAsString(product);
	 
	    mockMvc.perform(put(requestURI).contentType("application/json").content(requestBody))
	        .andExpect(status().isNotFound())
	        .andDo(print());       
	}
	
	@Test
	public void testUpdateShouldReturn400BadRequest() throws Exception {
		LOGGER.info("Testing testUpdateShouldReturn400BadRequest() if resource giving bad request");
	    Long productId = 123L;
	    String requestURI = END_POINT_PATH+"/products" + "/" + productId;
	 
	    Product product = new Product();
        product.setId(4L);
        product.setName("Delivery");
        product.setPrice(450);
        product.setQuantity(80);
	 
	    String requestBody = objectMapper.writeValueAsString(product);
	 
	    mockMvc.perform(put(requestURI).contentType("application/json").content(requestBody))
	        .andExpect(status().isBadRequest())
	        .andDo(print());
	}
	@Test
	public void testUpdateShouldReturn200OK() throws Exception {
		LOGGER.info("Testing testUpdateShouldReturn200OK() if resource available");
	    Long productId = 123L;
	    String requestURI = END_POINT_PATH+"/products" + "/" + productId;
	 
	    String name = "Shopping";
	    Product product = new Product();
        product.setId(3L);
        product.setName("Shopping");
        product.setPrice(350);
        product.setQuantity(20);
	 
	    Mockito.when(service.updateProduct(productId, product)).thenReturn(product);
	 
	    String requestBody = objectMapper.writeValueAsString(product);
	 
	    mockMvc.perform(put(requestURI).contentType("application/json").content(requestBody))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.name", is(name)))
	        .andDo(print());
	}
	
	@Test
	public void testDeleteShouldReturn404NotFound() throws Exception {
		LOGGER.info("Testing testDeleteShouldReturn404NotFound() if resource giving 404");
	    Long productId = 123L;
	    String requestURI = END_POINT_PATH + "/products/" + productId;
	 
	    Mockito.doThrow(ProductNotFoundException.class).when(service).deleteProduct(productId);
	 
	    mockMvc.perform(delete(requestURI))
	        .andExpect(status().isNotFound())
	        .andDo(print());
	}
	
	@Test
	public void testDeleteShouldReturn200OK() throws Exception {
		LOGGER.info("Testing testDeleteShouldReturn200OK() if resource available");
	    Long productId = 123L;
	    String requestURI = END_POINT_PATH + "/products/" + productId;
	 
	    Mockito.doNothing().when(service).deleteProduct(productId);
	 
	    mockMvc.perform(delete(requestURI))
	        .andExpect(status().isNoContent())
	        .andDo(print());
	}
	
	   

}
