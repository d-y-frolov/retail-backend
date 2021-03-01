package com.retail.backoffice.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.backoffice.domain.entities.Checks;

public interface CheckRepo extends JpaRepository<Checks, String> {

}
