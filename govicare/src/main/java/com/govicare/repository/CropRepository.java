package com.govicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.govicare.entity.Crop;

public interface CropRepository extends JpaRepository<Crop, Long> {

}
