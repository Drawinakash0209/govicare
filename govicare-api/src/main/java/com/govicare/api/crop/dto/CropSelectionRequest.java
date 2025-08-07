package com.govicare.api.crop.dto;

import java.util.Set;

import com.govicare.api.crop.Crop;

import lombok.Data;


@Data
public class CropSelectionRequest {
	
	private Set<String> cropNames;

}
