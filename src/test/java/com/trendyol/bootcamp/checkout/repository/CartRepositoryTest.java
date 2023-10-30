package com.trendyol.bootcamp.checkout.repository;

import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class CartRepositoryTest {
	@InjectMocks
	private CartRepository cartRepository;

	@Mock
	private Cart cart;

	@Test
	void test_should_reset_cart(){
		//given
		//when
		cartRepository.resetCard();

		//then
		assertEquals(new Cart(), cartRepository.getCart());
	}
}