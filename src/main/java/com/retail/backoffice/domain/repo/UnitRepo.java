package com.retail.backoffice.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.backoffice.domain.entities.Unit;

public interface UnitRepo extends JpaRepository<Unit, String> {

}
