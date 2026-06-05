package com.external.mock_service.service;

import com.external.mock_service.exception.ResourceNotFoundException;
import com.external.mock_service.model.BranchManagerResponse;
import com.external.mock_service.model.BranchRecord;
import com.external.mock_service.repository.BranchRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BranchService {

	private final BranchRepository branchRepository;
	private final UserService userService;

	public BranchService(BranchRepository branchRepository, UserService userService) {
		this.branchRepository = branchRepository;
		this.userService = userService;
	}

	public BranchRecord getByBranchId(String branchId) {
		return branchRepository.findById(branchId)
				.orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + branchId));
	}

	public BranchManagerResponse getManager(String branchId) {
		BranchRecord branch = getByBranchId(branchId);
		return new BranchManagerResponse(
				branch.branchId(),
				userService.getByUserId(branch.managerUserId())
		);
	}

	public BranchRecord getByUserId(String userId) {
		return branchRepository.findByManagerUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Branch not found for user: " + userId));
	}

	public List<BranchRecord> getBranches(Boolean active) {
		if (active == null) {
			return branchRepository.findAll();
		}
		return branchRepository.findAll().stream()
				.filter(branch -> branch.active() == active)
				.toList();
	}
}
