package com.govicare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.govicare.entity.UserCrop;

public interface UserCropRepository extends JpaRepository<UserCrop, Long> {
	List<UserCrop> findByUserId(Long userId);
}
