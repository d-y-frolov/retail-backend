package com.retail.backoffice.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="groups")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Groups {
	@Id
	@Column(name="group_id")
	String id;
	@Builder.Default
	String name="";
}
