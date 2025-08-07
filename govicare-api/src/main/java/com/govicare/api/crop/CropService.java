package com.govicare.api.crop;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.govicare.api.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CropService {
	
	private final CropRepository cropRepository;
	private final UserRepository userRepository;
	
	public List<Crop> getAllCrops(){
		return cropRepository.findAll();
	}
	
	
	@Transactional
	public void UpdateUserCrops(String userEmail, List<String> cropNames) {
		// Find the user by email
		var user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		Set<Crop> selectedCrops = cropNames.stream()
				.map(cropName -> cropRepository.findByName(cropName)
						.orElseThrow(() -> new RuntimeException("Crop not found: " + cropName)))
				.collect(Collectors.toSet());
		
		user.setSelectedCrops(selectedCrops);
		
		userRepository.save(user);

		
		userRepository.save(user);
	}
	
	
	public Set<Crop> getUserCrops(String userEmail) {
		var user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		return user.getSelectedCrops();
	}

}
