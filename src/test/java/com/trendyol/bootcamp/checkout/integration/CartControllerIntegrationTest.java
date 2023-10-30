package com.trendyol.bootcamp.checkout.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendyol.bootcamp.checkout.model.entity.item.DefaultItem;
import com.trendyol.bootcamp.checkout.model.request.AddItemRequest;
import com.trendyol.bootcamp.checkout.model.request.AddVasItemRequest;
import com.trendyol.bootcamp.checkout.model.request.RemoveItemRequest;
import com.trendyol.bootcamp.checkout.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CartControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CartRepository cartRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void test_getCartDetails_should_return_cart_details() throws Exception {
		mockMvc.perform(
				get("/api/v1/cart")
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk());
	}

	@Test
	void test_resetCart_should_reset_cart() throws Exception {
		mockMvc.perform(
						delete("/api/v1/cart")
								.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk());
	}

	@Test
	void test_removeItem_should_remove_item() throws Exception {
		var item = new DefaultItem(1, 1,1,1,1);
		cartRepository.saveItem(item);

		var removeItemRequest = new RemoveItemRequest();
		removeItemRequest.setItemId(1);

		mockMvc.perform(
						delete("/api/v1/cart/item")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(removeItemRequest))
				)
				.andExpect(status().isOk());
	}

	@Test
	void test_addItem_should_add_item() throws Exception {
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);
		addItemRequest.setCategoryId(1);
		addItemRequest.setSellerId(1);

		mockMvc.perform(
						post("/api/v1/cart/item")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(addItemRequest))
				)
				.andExpect(status().isCreated());
	}

	@Test
	void test_addVasItem_should_add_vas_item() throws Exception {
		var item = new DefaultItem(1, 1001,1,1,1);
		cartRepository.saveItem(item);

		var addItemRequest = new AddVasItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setVasItemId(10);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);
		addItemRequest.setCategoryId(3242);
		addItemRequest.setSellerId(5003);

		mockMvc.perform(
						post("/api/v1/cart/vas-item")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(addItemRequest))
				)
				.andExpect(status().isCreated());
	}

	@Test
	void test_addVasItem_should_return_not_found_when_item_id_is_not_valid() throws Exception {
		var item = new DefaultItem(1, 1001,1,1,1);
		cartRepository.saveItem(item);

		var addItemRequest = new AddVasItemRequest();
		addItemRequest.setItemId(2);
		addItemRequest.setVasItemId(10);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);
		addItemRequest.setCategoryId(3242);
		addItemRequest.setSellerId(5003);

		mockMvc.perform(
						post("/api/v1/cart/vas-item")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(addItemRequest))
				)
				.andExpect(status().isNotFound());
	}

	@Test
	void test_addVasItem_should_return_bad_request_when_category_id_is_not_valid() throws Exception {
		var item = new DefaultItem(1, 1001,1,1,1);
		cartRepository.saveItem(item);

		var addItemRequest = new AddVasItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setVasItemId(10);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);
		addItemRequest.setCategoryId(0);
		addItemRequest.setSellerId(5003);

		mockMvc.perform(
						post("/api/v1/cart/vas-item")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(addItemRequest))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	void test_addItem_should_return_bad_request_when_cart_limit_exceeded() throws Exception {
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(40);
		addItemRequest.setCategoryId(1);
		addItemRequest.setSellerId(1);

		mockMvc.perform(
						post("/api/v1/cart/item")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(addItemRequest))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	void test_addItem_should_return_bad_request_when_cart_has_different_type_item() throws Exception {
		var item = new DefaultItem(1, 1001,1,1,1);
		cartRepository.saveItem(item);

		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(2);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);
		addItemRequest.setCategoryId(7889);
		addItemRequest.setSellerId(1);

		mockMvc.perform(
						post("/api/v1/cart/item")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(addItemRequest))
				)
				.andExpect(status().isBadRequest());
	}
}
