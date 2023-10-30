package com.trendyol.bootcamp.checkout.service;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import com.trendyol.bootcamp.checkout.model.entity.item.DefaultItem;
import com.trendyol.bootcamp.checkout.model.request.RemoveItemRequest;
import com.trendyol.bootcamp.checkout.properties.CartProperties;
import com.trendyol.bootcamp.checkout.repository.CartRepository;
import com.trendyol.bootcamp.checkout.util.PromotionCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
	void it_should_remove_item(){
		//given
		var removeItemRequest = new RemoveItemRequest();
		removeItemRequest.setItemId(1);

		var itemToRemove = new DefaultItem(1,1,1,1,1);

		var expectedPromotion = PromotionDTO.builder()
				.promotionId(0)
				.totalDiscount(0)
				.build();

		var cart = new Cart(new ArrayList<>(), 0, 0 ,0);


		when(cartRepository.getItem(removeItemRequest.getItemId())).thenReturn(Optional.of(itemToRemove));
		when(cartRepository.getCart()).thenReturn(cart);
		when(promotionCalculator.getSuitablePromotion(cart)).thenReturn(expectedPromotion);



		//when
		cartService.removeItem(removeItemRequest);

		//then
		verify(cartRepository, times(1)).deleteItem(itemToRemove);
		verify(cartRepository, times(1))
				.updateTotalPrice(itemToRemove.getQuantity() * itemToRemove.getPrice()* -1);
		verify(cartRepository, times(1)).setPromotion(expectedPromotion);

	}

	@Test
	void it_should_reset_cart(){
		//given
		//when
		cartService.resetCart();
		//then
		verify(cartRepository, times(1)).resetCard();
	}

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