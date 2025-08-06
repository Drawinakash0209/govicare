package com.govicare.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Crop {
	
	// Default constructor for JPA
	public Crop() {
		// No-arg constructor for JPA
	}
	
	// Constructor with ID for convenience
	public Crop(Long id) {
		this.id = id;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

}
