package com.retail.backoffice.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="cash")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cash {
	@Id
	@Column(name="cash_id")
	int id;
//	@Column(name="name", columnDefinition = "varchar(255) default ''")
	@Builder.Default
	String name="";
//	@Column(name="info", columnDefinition = "varchar(255) default ''")
	@Builder.Default
	String info="";
	@Column(name="last_check_number")
	int lastCheckNumber;
	@Column(name="check_prefix")
	@Builder.Default
	private String checkPrefix = "";
}
