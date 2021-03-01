package com.retail.backoffice.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.retail.backoffice.domain.entities.SalesDateSumEntity;

public interface SalesDateSumRepo extends JpaRepository<SalesDateSumEntity, String> {
	@Query(value="select formatdatetime(date_time,'yyyy-MM-dd') as date, round(sum(c.sum),2) as sum " + 
			"from checks c " + 
			"where convert(c.date_time,date) >= convert(:from,date)  and convert(c.date_time, date) <= convert(:to,date) " + 
			"group by date " + 
			"order by date",nativeQuery=true)
	List<SalesDateSumEntity> getSalesDateSum(@Param("from") String start,@Param("to") String end);
}
