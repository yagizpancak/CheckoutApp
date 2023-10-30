package com.trendyol.bootcamp.checkout.util;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TotalPricePromotionTest {
	@InjectMocks
	private TotalPricePromotion totalPricePromotion;

	@Test
	void test_checkPromotion_should_return_true(){
		//given
		var cart = new Cart();

		//when
		var response = totalPricePromotion.checkPromotion(cart);

		//then
		assertTrue(response);
	}

	@ParameterizedTest
	@MethodSource("cartsAndPromotionDTOs")
	void test_applyPromotion_should_return_as_expected(Cart cart, PromotionDTO promotion){
		//given

		//when
		var response = totalPricePromotion.applyPromotion(cart);

		//then
		assertEquals(promotion.getTotalDiscount(), response.getTotalDiscount());
	}

	private static Stream<Arguments> cartsAndPromotionDTOs() {
		return Stream.of(
				Arguments.of(Cart.builder().totalPrice(2500).build(), PromotionDTO.builder().totalDiscount(250).build()),
				Arguments.of(Cart.builder().totalPrice(7500).build(), PromotionDTO.builder().totalDiscount(500).build()),
				Arguments.of(Cart.builder().totalPrice(25000).build(), PromotionDTO.builder().totalDiscount(1000).build()),
				Arguments.of(Cart.builder().totalPrice(75000).build(), PromotionDTO.builder().totalDiscount(2000).build())
		);
	}
}