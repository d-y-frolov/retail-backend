package com.retail.backoffice.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="check_detail")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="detail_id")
	long detailId;

	@ManyToOne
	Checks check;

	@ManyToOne
	Product product;
	
	double quantity;
	double price;
	double sum;
}
