package com.external.mock_service.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MockControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturnUserById() throws Exception {
		mockMvc.perform(get("/mock/users/13038"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userId", is("13038")))
				.andExpect(jsonPath("$.departmentName", is("Operator departamenti")))
				.andExpect(jsonPath("$.section", is("Mühafizə-rejim və əməyin təhlükəsizliyi şöbəsi")));
	}

	@Test
	void shouldReturnDepartmentByUserId() throws Exception {
		mockMvc.perform(get("/mock/users/13038/department"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.departmentId", is("OPS-001")))
				.andExpect(jsonPath("$.departmentName", is("Operator departamenti")));
	}

	@Test
	void shouldFilterActiveUsers() throws Exception {
		mockMvc.perform(get("/mock/users").param("active", "true"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(24)))
				.andExpect(jsonPath("$[0].userId", is("10001")));
	}

	@Test
	void shouldReturnBranchById() throws Exception {
		mockMvc.perform(get("/mock/branches/BR-101"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.branchId", is("BR-101")))
				.andExpect(jsonPath("$.managerUserId", is("10002")));
	}

	@Test
	void shouldReturnBranchManager() throws Exception {
		mockMvc.perform(get("/mock/branches/BR-101/manager"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.branchId", is("BR-101")))
				.andExpect(jsonPath("$.manager.userId", is("10002")))
				.andExpect(jsonPath("$.manager.section", is("Port Baku filialı")));
	}

	@Test
	void shouldReturnBranchesByManager() throws Exception {
		mockMvc.perform(get("/mock/branches/by-manager/10002"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.branchId", is("BR-101")))
				.andExpect(jsonPath("$.branchName", is("Port Baku filialı")));
	}

	@Test
	void shouldReturnNotFoundForRegularUserId() throws Exception {
		mockMvc.perform(get("/mock/branches/by-manager/10010"))
				.andExpect(status().isNotFound());
	}
}
