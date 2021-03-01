package com.retail.backoffice.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table ( name="product")
@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {
	@Id()
	private String id;
	private String name;

	@ManyToOne
	@JoinColumn(name="group_id")
	private Groups group;
	
	@ManyToOne
	@JoinColumn(name="unit_id")
	private Unit unit;
	
	private Double tax;
	private Double price;
	private Double remainder;

	String manufacturer;
	String country;
}
