package com.retail.backoffice.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dima
 *
 */
@Entity
@Table(name = "unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Unit {
	@Id
	@Column(name = "unit_id")
	private String id;
	@Builder.Default
	private String name="";
	@Column(name="is_piece")
	@Builder.Default
	private boolean pieceUnit = true;  
}
