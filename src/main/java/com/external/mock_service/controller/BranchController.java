package com.external.mock_service.controller;

import com.external.mock_service.model.BranchManagerResponse;
import com.external.mock_service.model.BranchRecord;
import com.external.mock_service.service.BranchService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock/branches")
public class BranchController {

	private final BranchService branchService;

	public BranchController(BranchService branchService) {
		this.branchService = branchService;
	}

	@GetMapping("/{branchId}")
	public BranchRecord getByBranchId(@PathVariable String branchId) {
		return branchService.getByBranchId(branchId);
	}

	@GetMapping("/{branchId}/manager")
	public BranchManagerResponse getManager(@PathVariable String branchId) {
		return branchService.getManager(branchId);
	}

	@GetMapping("/by-manager/{userId}")
	public BranchRecord getByManagerUserId(@PathVariable String userId) {
		return branchService.getByUserId(userId);
	}

	@GetMapping
	public List<BranchRecord> getBranches(@RequestParam(required = false) Boolean active) {
		return branchService.getBranches(active);
	}
}
