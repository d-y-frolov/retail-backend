package com.retail.backoffice.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.backoffice.domain.entities.Cash;

public interface CashRepo extends JpaRepository<Cash, Integer> {

}
