package com.trendyol.bootcamp.checkout.controller;

import com.trendyol.bootcamp.checkout.model.request.RemoveItemRequest;
import com.trendyol.bootcamp.checkout.model.response.ApplicationResponse;
import com.trendyol.bootcamp.checkout.model.response.cart.CartResponse;
import com.trendyol.bootcamp.checkout.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

	private final CartService cartService;

	@Value("${controller.addItemResponse}")
	private String addItemResponse;
	@Value("${controller.addVasItemResponse}")
	private String addVasItemResponse;
	@Value("${controller.removeItemResponse}")
	private String removeItemResponse;
	@Value("${controller.resetCartResponse}")
	private String resetCartResponse;


	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@DeleteMapping("/item")
	public ResponseEntity<ApplicationResponse<String>> removeItem(@RequestBody RemoveItemRequest removeItemRequest){
		cartService.removeItem(removeItemRequest);
		return ResponseEntity.status(HttpStatus.OK).body(
				ApplicationResponse.<String>builder()
						.result(true)
						.message(removeItemResponse)
						.build()
		);
	}


	@DeleteMapping()
	public ResponseEntity<ApplicationResponse<String>> resetCart(){
		cartService.resetCart();
		return ResponseEntity.status(HttpStatus.OK).body(
				ApplicationResponse.<String>builder()
						.result(true)
						.message(resetCartResponse)
						.build()
		);
	}


	@GetMapping()
	public ResponseEntity<ApplicationResponse<CartResponse>> getCartDetails(){
		return ResponseEntity.status(HttpStatus.OK).body(
				ApplicationResponse.<CartResponse>builder()
						.result(true)
						.message(cartService.displayCart())
						.build()
		);
	}
}
