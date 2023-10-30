package com.trendyol.bootcamp.checkout.util;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PromotionCalculatorTest {
	@InjectMocks
	private PromotionCalculator promotionCalculator;
	@Mock
	private CategoryPromotion categoryPromotion;
	@Mock
	private SameSellerPromotion sameSellerPromotion;
	@Mock
	private TotalPricePromotion totalPricePromotion;

	@BeforeEach
	public void setup() {
		List<Promotion> promotionList = Arrays.asList(categoryPromotion, sameSellerPromotion, totalPricePromotion);
		promotionCalculator = new PromotionCalculator(promotionList);
	}
	@Test
	void test_should_return_same_seller_promotion_when_applicable(){
		//given
		var cart = new Cart();
		var expected = PromotionDTO.builder().build();

		when(sameSellerPromotion.checkPromotion(cart)).thenReturn(true);
		when(sameSellerPromotion.applyPromotion(cart)).thenReturn(expected);

		//when
		var actual = promotionCalculator.getSuitablePromotion(cart);

		//then
		assertEquals(expected, actual);
	}

	@Test
	void test_should_return_category_promotion_when_applicable(){
		//given
		var cart = new Cart();
		var expected = PromotionDTO.builder().build();

		when(categoryPromotion.checkPromotion(cart)).thenReturn(true);
		when(categoryPromotion.applyPromotion(cart)).thenReturn(expected);

		//when
		var actual = promotionCalculator.getSuitablePromotion(cart);

		//then
		assertEquals(expected, actual);
	}

	@Test
	void test_should_return_total_price_promotion_when_applicable(){
		//given
		var cart = new Cart();
		var expected = PromotionDTO.builder().build();

		when(totalPricePromotion.checkPromotion(cart)).thenReturn(true);
		when(totalPricePromotion.applyPromotion(cart)).thenReturn(expected);

		//when
		var actual = promotionCalculator.getSuitablePromotion(cart);

		//then
		assertEquals(expected, actual);
	}

	@Test
	void test_should_return_same_seller_promotion_when_the_highest(){
		//given
		var cart = new Cart();
		var expected = PromotionDTO.builder()
				.promotionId(9909)
				.totalDiscount(300)
				.build();

		var categoryPromotionDTO = PromotionDTO.builder()
				.promotionId(5676)
				.totalDiscount(100)
				.build();

		var totalPricePromotionDTO = PromotionDTO.builder()
				.promotionId(1232)
				.totalDiscount(200)
				.build();

		when(sameSellerPromotion.checkPromotion(cart)).thenReturn(true);
		when(sameSellerPromotion.applyPromotion(cart)).thenReturn(expected);
		when(categoryPromotion.checkPromotion(cart)).thenReturn(true);
		when(categoryPromotion.applyPromotion(cart)).thenReturn(categoryPromotionDTO);
		when(totalPricePromotion.checkPromotion(cart)).thenReturn(true);
		when(totalPricePromotion.applyPromotion(cart)).thenReturn(totalPricePromotionDTO);

		//when
		var actual = promotionCalculator.getSuitablePromotion(cart);

		//then
		assertEquals(expected, actual);
	}

	@Test
	void test_should_return_category_promotion_when_the_highest(){
		//given
		var cart = new Cart();
		var sameSellerPromotionDTO = PromotionDTO.builder()
				.promotionId(9909)
				.totalDiscount(100)
				.build();

		var expected = PromotionDTO.builder()
				.promotionId(5676)
				.totalDiscount(300)
				.build();

		var totalPricePromotionDTO = PromotionDTO.builder()
				.promotionId(1232)
				.totalDiscount(200)
				.build();

		when(sameSellerPromotion.checkPromotion(cart)).thenReturn(true);
		when(sameSellerPromotion.applyPromotion(cart)).thenReturn(sameSellerPromotionDTO);
		when(categoryPromotion.checkPromotion(cart)).thenReturn(true);
		when(categoryPromotion.applyPromotion(cart)).thenReturn(expected);
		when(totalPricePromotion.checkPromotion(cart)).thenReturn(true);
		when(totalPricePromotion.applyPromotion(cart)).thenReturn(totalPricePromotionDTO);

		//when
		var actual = promotionCalculator.getSuitablePromotion(cart);

		//then
		assertEquals(expected, actual);
	}


	@Test
	void test_should_return_total_price_promotion_when_the_highest(){
		//given
		var cart = new Cart();
		var sameSellerPromotionDTO = PromotionDTO.builder()
				.promotionId(9909)
				.totalDiscount(100)
				.build();

		var categoryPromotionDTO = PromotionDTO.builder()
				.promotionId(5676)
				.totalDiscount(200)
				.build();

		var expected = PromotionDTO.builder()
				.promotionId(1232)
				.totalDiscount(300)
				.build();

		when(sameSellerPromotion.checkPromotion(cart)).thenReturn(true);
		when(sameSellerPromotion.applyPromotion(cart)).thenReturn(sameSellerPromotionDTO);
		when(categoryPromotion.checkPromotion(cart)).thenReturn(true);
		when(categoryPromotion.applyPromotion(cart)).thenReturn(categoryPromotionDTO);
		when(totalPricePromotion.checkPromotion(cart)).thenReturn(true);
		when(totalPricePromotion.applyPromotion(cart)).thenReturn(expected);

		//when
		var actual = promotionCalculator.getSuitablePromotion(cart);

		//then
		assertEquals(expected, actual);
	}
}