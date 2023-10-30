package com.trendyol.bootcamp.checkout.repository;

import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CartRepositoryTest {
	@InjectMocks
	private CartRepository cartRepository;

	@Mock
	private Cart cart;


}