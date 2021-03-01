package com.retail.backoffice.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.retail.backoffice.domain.entities.Product;

public interface ProductRepo extends JpaRepository<Product, String> {
	@Query(value="SELECT * FROM product p WHERE p.id LIKE %:searchString% OR p.name LIKE %:searchString%", 
			nativeQuery = true)
	List<Product> searchByIdOrName(@Param(value = "searchString") String searchString);

	@Query(value="SELECT p FROM Product p WHERE UPPER(p.name) LIKE %:searchString%", 
			nativeQuery = false)
	List<Product> searchByName(@Param(value = "searchString") String searchString);

}
