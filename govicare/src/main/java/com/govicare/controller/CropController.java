package com.govicare.controller;

import java.net.http.WebSocket.Listener;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.govicare.entity.Crop;
import com.govicare.entity.UserCrop;
import com.govicare.service.CropService;

@RestController
@RequestMapping("/api/crops")
public class CropController {
	
	@Autowired
	private CropService cropService;
	
	@GetMapping
	public ResponseEntity<List<Crop>> getAllCrops(){
		return ResponseEntity.ok(cropService.getAllCrops());
	}
	
	@PostMapping("/select")
	public ResponseEntity<UserCrop> selectCrop(@RequestBody Map<String, Long> request){
		return ResponseEntity.ok(cropService.addUserCrop(request.get("userId"), request.get("cropId")));
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<UserCrop>> getUserCrops(@PathVariable Long userId) {
		return ResponseEntity.ok(cropService.getUserCrops(userId));

	}

}
