package com.govicare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.govicare.entity.Crop;
import com.govicare.entity.User;
import com.govicare.entity.UserCrop;
import com.govicare.repository.CropRepository;
import com.govicare.repository.UserCropRepository;
import com.govicare.repository.UserRepository;

@Service
public class CropService {
	@Autowired
	private CropRepository cropRepository;
	@Autowired
	private UserCropRepository userCropRepository;
	
	public List<Crop> getAllCrops(){
		return cropRepository.findAll();
	}
	
	public UserCrop addUserCrop(Long userId, Long CropId) {
		UserCrop userCrop = new UserCrop();
		userCrop.setUser(new User(userId));
		userCrop.setCrop(new Crop(CropId));
		
		return userCropRepository.save(userCrop);
	}
	
	public List<UserCrop> getUserCrops(Long userId){
		return userCropRepository.findByUserId(userId);
	}

}
