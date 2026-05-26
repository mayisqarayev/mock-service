package com.external.mock_service.model;

public record BranchRecord(
		String branchId,
		String branchName,
		String managerUserId,
		boolean active,
		String region
) {
}
