package com.restapi.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//Constructors, getters and setters, and other methods...

// Getters
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@Column(nullable = false)
    private String name;
    
	@Column(nullable = false)
    private double price;
 
	@Column(nullable = false)
    private int quantity;

}
