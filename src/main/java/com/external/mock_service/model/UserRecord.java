package com.external.mock_service.model;

public record UserRecord(
		String userId,
		String username,
		String fullName,
		String email,
		String departmentId,
		String departmentName,
		boolean active,
		String position
) {
}
