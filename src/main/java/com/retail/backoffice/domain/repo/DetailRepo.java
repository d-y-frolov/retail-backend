package com.retail.backoffice.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.retail.backoffice.domain.entities.CheckDetail;

public interface DetailRepo extends JpaRepository<CheckDetail, Long> {
	@Query(value="select p.group_id, g.name, formatdatetime(date_time,'yyyy-MM-dd') as date, round(sum(d.sum),2) as sum "
			+ "from check_detail d " + 
			"join checks c on d.check_check_id = c.check_id " + 
			"join product p on d.product_id = p.id " + 
			"join groups g on p.group_id = g.group_id " + 
			"where convert(c.date_time,date) >= convert(:from,date)  and convert(c.date_time, date) <= convert(:to, date) " + 
			"group by p.group_id, g.name, date " + 
			"order by p.group_id, g.name, date" 
			,nativeQuery=true)
	List<Object[]> getReportCashSale(@Param("from") String start,@Param("to") String end);

}
