package com.external.mock_service.repository;

import com.external.mock_service.config.MockDataProperties;
import com.external.mock_service.model.UserRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	private final JsonDataLoader jsonDataLoader;
	private final MockDataProperties mockDataProperties;

	private List<UserRecord> users;
	private Map<String, UserRecord> usersById;
	private Map<String, UserRecord> usersByUsername;
	private Map<String, List<UserRecord>> usersByBranchId;

	public UserRepository(JsonDataLoader jsonDataLoader, MockDataProperties mockDataProperties) {
		this.jsonDataLoader = jsonDataLoader;
		this.mockDataProperties = mockDataProperties;
	}

	@PostConstruct
	void init() {
		users = List.copyOf(jsonDataLoader.loadList(
				mockDataProperties.usersLocation(),
				new TypeReference<>() {
				}
		));
		usersById = users.stream().collect(Collectors.toUnmodifiableMap(UserRecord::userId, Function.identity()));
		usersByUsername = users.stream().collect(
				Collectors.toUnmodifiableMap(user -> user.username().toLowerCase(), Function.identity())
		);
		usersByBranchId = users.stream().collect(
				Collectors.groupingBy(
						UserRecord::branchId,
						Collectors.collectingAndThen(Collectors.toList(), List::copyOf)
				)
		);
	}

	public List<UserRecord> findAll() {
		return users;
	}

	public Optional<UserRecord> findById(String userId) {
		return Optional.ofNullable(usersById.get(userId));
	}

	public Optional<UserRecord> findByUsername(String username) {
		return Optional.ofNullable(usersByUsername.get(username.toLowerCase()));
	}

	public List<UserRecord> findByBranchId(String branchId) {
		return users.stream().filter(userRecord -> userRecord.branchId().equals(branchId))
				.collect(Collectors.toList());
	}
}
