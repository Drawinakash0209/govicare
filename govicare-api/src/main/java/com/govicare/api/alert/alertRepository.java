package com.govicare.api.alert;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface alertRepository extends JpaRepository<Alert, Long> {
	
	List<Alert> findByUser_IdOrderByCreatedAtDesc(Long userId); // Find alerts by user ID

}
