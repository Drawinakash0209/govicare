package com.govicare.api.crop;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.govicare.api.crop.dto.CropSelectionRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {
	
	private final CropService cropService;
	
	
	@GetMapping
	public ResponseEntity<List<Crop>> getAllCrops() {
		List<Crop> crops = cropService.getAllCrops();
		return ResponseEntity.ok(crops);
	}
	
	@GetMapping("/my-crops")
	public ResponseEntity<Set<Crop>> getUserCrops(String userEmail) {
		Set<Crop> userCrops = cropService.getUserCrops(userEmail);
		return ResponseEntity.ok(userCrops);
	}
	
	@PostMapping("/my-crops")
	
	public ResponseEntity<Void> updateUserCrops(@RequestBody CropSelectionRequest request, Principal principal) {
		String userEmail = principal.getName();
		cropService.UpdateUserCrops(userEmail, List.copyOf(request.getCropNames()));
		return ResponseEntity.ok().build();
	}

}
