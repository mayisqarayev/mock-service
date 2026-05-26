package com.external.mock_service.model;

public record BranchManagerResponse(
		String branchId,
		UserRecord manager
) {
}
