package com.trendyol.bootcamp.checkout.service;

import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;import com.trendyol.bootcamp.checkout.properties.CartProperties;
import com.trendyol.bootcamp.checkout.repository.CartRepository;
import com.trendyol.bootcamp.checkout.util.PromotionCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CartServiceTest {
	@InjectMocks
	private CartService cartService;

	@Mock
	private CartProperties cartProperties;
	@Mock
	private CartRepository cartRepository;
	@Mock
	private PromotionCalculator promotionCalculator;


	@Test
	void it_should_display_cart(){
		//given
		var cart = new Cart(new ArrayList<>(), 0, 0 ,0);
		var expected = cart.toResponse();

		when(cartRepository.getCart()).thenReturn(cart);

		//when
		var actual = cartService.displayCart();

		//then
		verify(cartRepository, times(1)).getCart();
		assertEquals(expected, actual);
	}


}