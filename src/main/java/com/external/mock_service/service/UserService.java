package com.external.mock_service.service;

import com.external.mock_service.exception.ResourceNotFoundException;
import com.external.mock_service.model.DepartmentResponse;
import com.external.mock_service.model.UserRecord;
import com.external.mock_service.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserRecord getByUserId(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
	}

	public UserRecord getByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found by username: " + username));
	}

	public DepartmentResponse getDepartment(String userId) {
		UserRecord user = getByUserId(userId);
		return new DepartmentResponse(user.userId(), user.departmentId(), user.departmentName());
	}

	public List<UserRecord> getUsers(Boolean active) {
		if (active == null) {
			return userRepository.findAll();
		}
		return userRepository.findAll().stream()
				.filter(user -> user.active() == active)
				.toList();
	}
}
