package com.trendyol.bootcamp.checkout.repository;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import com.trendyol.bootcamp.checkout.model.entity.item.DefaultItem;
import com.trendyol.bootcamp.checkout.model.entity.item.DigitalItem;
import com.trendyol.bootcamp.checkout.model.entity.item.Item;
import com.trendyol.bootcamp.checkout.model.entity.item.VasItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

	@Test
	void test_should_save_item(){
		//given
		var item = new DefaultItem(1,1,1,1,1);
		var expectedCart = new Cart(Collections.singletonList(item), 1, 0 ,0);

		List<Item> items = new ArrayList<>();
		when(cart.getItems()).thenReturn(items);

		//when
		cartRepository.saveItem(item);

		//then
		assertEquals(expectedCart.getItems(), cartRepository.getCart().getItems());
	}

	@Test
	void test_should_update_item(){
		//given
		var item = new DefaultItem(1,1,1,1,1);
		var expectedItem = new DefaultItem(1,1,1,1,2)	;

		List<Item> items = Collections.singletonList(item);
		when(cart.getItems()).thenReturn(items);

		//when
		cartRepository.saveItem(item);

		//then
		assertEquals(expectedItem, cartRepository.getCart().getItems().get(0));
	}

	@Test
	void test_should_get_item(){
		//given
		var item = new DefaultItem(1,1,1,1,1);
		var expectedItem = new DefaultItem(1,1,1,1,1)	;

		List<Item> items = Collections.singletonList(item);
		when(cart.getItems()).thenReturn(items);

		//when
		var actualItem = cartRepository.getItem(item.getId()).get();

		//then
		assertEquals(expectedItem, actualItem);
	}

	@Test
	void test_should_delete_item(){
		//given
		var item = new DefaultItem(1,1,1,1,1);

		List<Item> items = new ArrayList<>();
		items.add(item);
		when(cart.getItems()).thenReturn(items);

		//when
		cartRepository.deleteItem(item);

		//then
		assertEquals(new ArrayList<>(), cartRepository.getCart().getItems());
	}

	@Test
	void test_should_set_promotion(){
		//given
		var promotionDTO = PromotionDTO.builder()
				.promotionId(0)
				.totalDiscount(0)
				.build();

		//when
		cartRepository.setPromotion(promotionDTO);

		//then
		verify(cart, times(1)).setAppliedPromotionId(promotionDTO.getPromotionId());
		verify(cart, times(1)).setTotalDiscount(promotionDTO.getTotalDiscount());

	}

	@Test
	void test_hasCartDigitalItem_should_return_true_when_cart_has_digital_item(){
		//given
		var item = new DigitalItem(1, 1, 1,1 ,1);

		List<Item> items = Collections.singletonList(item);
		when(cart.getItems()).thenReturn(items);

		//when
		var response = cartRepository.hasCartDigitalItem();

		//then
		assertTrue(response);
	}

	@Test
	void test_hasCartDigitalItem_should_return_false_when_cart_has_no_digital_item(){
		//given
		var item = new DefaultItem(1, 1, 1,1 ,1);

		List<Item> items = Collections.singletonList(item);
		when(cart.getItems()).thenReturn(items);

		//when
		var response = cartRepository.hasCartDigitalItem();

		//then
		assertFalse(response);
	}

	@Test
	void test_hasCartDefaultItem_should_return_true_when_cart_has_default_item(){
		//given
		var item = new DefaultItem(1, 1, 1,1 ,1);

		List<Item> items = Collections.singletonList(item);
		when(cart.getItems()).thenReturn(items);

		//when
		var response = cartRepository.hasCartDefaultItem();

		//then
		assertTrue(response);
	}

	@Test
	void test_hasCartDefaultItem_should_return_false_when_cart_has_no_default_item(){
		//given
		var item = new DigitalItem(1, 1, 1,1 ,1);

		List<Item> items = Collections.singletonList(item);
		when(cart.getItems()).thenReturn(items);

		//when
		var response = cartRepository.hasCartDefaultItem();

		//then
		assertFalse(response);
	}

	@Test
	void test_should_return_total_unique_item_number(){
		//given
		var firstItem = new DefaultItem(1,1,1,1,1);
		var secondItem = new DefaultItem(2,2,2,2,2);

		List<Item> items = new ArrayList<>();
		items.add(firstItem);
		items.add(secondItem);
		when(cart.getItems()).thenReturn(items);

		//when
		var response = cartRepository.getTotalUniqueItemNumber();

		//then
		assertEquals(items.size(), response);
	}

	@Test
	void test_should_return_total_item_number(){
		//given
		var firstItem = new DefaultItem(1,1,1,1,5);
		var secondItem = new DefaultItem(2,2,2,2,2);
		var vasItem = new VasItem(3,1,1,1,2);

		var expected = firstItem.getQuantity() + secondItem.getQuantity() + vasItem.getQuantity();

		firstItem.addVasItem(vasItem);

		List<Item> items = new ArrayList<>();
		items.add(firstItem);
		items.add(secondItem);
		when(cart.getItems()).thenReturn(items);

		//when
		var actual = cartRepository.getTotalItemNumber();

		//then
		assertEquals(expected, actual);
	}

	@Test
	void test_should_update_total_price(){
		//given
		var newItemPrice = 10.0;
		var cartTotalPrice = 10.0;
		var expected = newItemPrice + cartTotalPrice;
		when(cart.getTotalPrice()).thenReturn(cartTotalPrice);

		//when
		cartRepository.updateTotalPrice(newItemPrice);

		//then
		verify(cart, times(1)).setTotalPrice(expected);
	}

	@Test
	void test_should_return_total_price(){
		//given
		var expected = 10.0;
		when(cart.getTotalPrice()).thenReturn(expected);

		//when
		var actual = cartRepository.getTotalPrice();

		//then
		assertEquals(expected, actual);
	}
}