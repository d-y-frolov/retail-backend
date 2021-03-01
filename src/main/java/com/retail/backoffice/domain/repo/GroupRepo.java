package com.retail.backoffice.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.backoffice.domain.entities.Groups;

public interface GroupRepo extends JpaRepository<Groups, String> {

}
