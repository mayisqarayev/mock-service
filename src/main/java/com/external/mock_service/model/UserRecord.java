package com.external.mock_service.model;

public record UserRecord(
		String userId,
		String username,
		String fullName,
		String email,
		String departmentId,
		String departmentName,
		String section,
		boolean active,
		String position
) {
}
