package com.trendyol.bootcamp.checkout.util;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import com.trendyol.bootcamp.checkout.model.entity.item.DefaultItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SameSellerPromotionTest {

	@InjectMocks
	private SameSellerPromotion sameSellerPromotion;

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(sameSellerPromotion, "discountPercentage", 0.1);
	}
	@Test
	void test_checkPromotion_should_return_true_when_cart_has_one_item(){
		//given
		var cart = new Cart();
		cart.getItems().add(new DefaultItem(1,1,1,1,1));

		//when
		var response = sameSellerPromotion.checkPromotion(cart);

		//then
		assertTrue(response);
	}

	@Test
	void test_checkPromotion_should_return_true_when_cart_has_two_same_seller_item(){
		//given
		var cart = new Cart();
		cart.getItems().add(new DefaultItem(1,1,1,1,1));
		cart.getItems().add(new DefaultItem(2,2,1,2,2));

		//when
		var response = sameSellerPromotion.checkPromotion(cart);

		//then
		assertTrue(response);
	}

	@Test
	void test_checkPromotion_should_return_false_when_cart_has_two_different_seller_item(){
		//given
		var cart = new Cart();
		cart.getItems().add(new DefaultItem(1,1,1,1,1));
		cart.getItems().add(new DefaultItem(2,2,2,2,2));

		//when
		var response = sameSellerPromotion.checkPromotion(cart);

		//then
		assertFalse(response);
	}

	@Test
	void test_applyPromotion_should_return_as_expected(){
		//given
		var cart = new Cart(new ArrayList<>(), 1000 ,0 ,0);
		var promotion = PromotionDTO.builder().totalDiscount(100).build();

		//when
		var response = sameSellerPromotion.applyPromotion(cart);

		//then
		assertEquals(promotion.getTotalDiscount(), response.getTotalDiscount());
	}
}