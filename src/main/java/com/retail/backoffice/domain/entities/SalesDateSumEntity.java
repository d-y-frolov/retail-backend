package com.retail.backoffice.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDateSumEntity {
	@Id
	@Column(name="date")
	private String stringDate ;
	private Double sum ;

}
