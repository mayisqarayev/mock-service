package com.external.mock_service.controller;

import com.external.mock_service.model.DepartmentResponse;
import com.external.mock_service.model.UserRecord;
import com.external.mock_service.service.UserService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{userId}")
	public UserRecord getByUserId(@PathVariable String userId) {
		return userService.getByUserId(userId);
	}

	@GetMapping("/by-username/{username}")
	public UserRecord getByUsername(@PathVariable String username) {
		return userService.getByUsername(username);
	}

	@GetMapping("/{userId}/department")
	public DepartmentResponse getDepartment(@PathVariable String userId) {
		return userService.getDepartment(userId);
	}

	@GetMapping
	public List<UserRecord> getUsers(
			@RequestParam(required = false) Boolean active,
			@RequestParam(required = false) String branchId
	) {
		return userService.getUsers(active, branchId);
	}
}
