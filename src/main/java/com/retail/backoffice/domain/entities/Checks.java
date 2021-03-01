package com.retail.backoffice.domain.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="checks")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Checks {
	@Id
	@Column(name="check_id")
	String id;
	
	@ManyToOne
	Cash cash;

	@OneToMany(mappedBy= "check",fetch = FetchType.EAGER)
	List<CheckDetail> details;
	
	@Column(name="date_time")
	LocalDateTime dateTime;

	Double sum;
}

