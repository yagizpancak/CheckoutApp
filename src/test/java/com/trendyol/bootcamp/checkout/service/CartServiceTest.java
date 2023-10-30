package com.trendyol.bootcamp.checkout.service;

import com.trendyol.bootcamp.checkout.model.dto.PromotionDTO;
import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import com.trendyol.bootcamp.checkout.model.entity.item.DefaultItem;
import com.trendyol.bootcamp.checkout.model.entity.item.DigitalItem;
import com.trendyol.bootcamp.checkout.model.exception.*;
import com.trendyol.bootcamp.checkout.model.request.AddItemRequest;
import com.trendyol.bootcamp.checkout.model.request.AddVasItemRequest;
import com.trendyol.bootcamp.checkout.model.request.RemoveItemRequest;
import com.trendyol.bootcamp.checkout.properties.CartProperties;
import com.trendyol.bootcamp.checkout.repository.CartRepository;
import com.trendyol.bootcamp.checkout.util.PromotionCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
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
	void it_should_add_default_item() {
		//given
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setCategoryId(1);
		addItemRequest.setSellerId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);

		var expectedPromotion = PromotionDTO.builder()
				.promotionId(0)
				.totalDiscount(0)
				.build();

		var cart = new Cart(Collections.singletonList(addItemRequest.toDefaultItemEntity()), 10, 0 ,0);

		when(cartRepository.getTotalItemNumber()).thenReturn(0);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(0);
		when(cartProperties.getMaxUniqueItem()).thenReturn(10);
		when(cartRepository.getCart()).thenReturn(cart);
		when(cartProperties.getMaxTotalPrice()).thenReturn(500000.0);
		when(cartProperties.getDigitalItemCategoryId()).thenReturn(7889);
		when(cartRepository.hasCartDigitalItem()).thenReturn(false);
		when(cartProperties.getMaxItemQuantity()).thenReturn(10);
		when(cartRepository.getItem(addItemRequest.getItemId())).thenReturn(Optional.empty());


		when(promotionCalculator.getSuitablePromotion(cart)).thenReturn(expectedPromotion);

		//when
		cartService.addItem(addItemRequest);

		//then
		verify(cartRepository, times(1)).saveItem(addItemRequest.toDefaultItemEntity());
		verify(cartRepository, times(1))
				.updateTotalPrice(addItemRequest.getQuantity() * addItemRequest.getPrice());
		verify(cartRepository, times(1)).setPromotion(expectedPromotion);
	}

	@Test
	void it_should_add_digital_item() {
		//given
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setCategoryId(7889);
		addItemRequest.setSellerId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);

		var expectedPromotion = PromotionDTO.builder()
				.promotionId(0)
				.totalDiscount(0)
				.build();

		var cart = new Cart(Collections.singletonList(addItemRequest.toDefaultItemEntity()), 10, 0 ,0);

		when(cartRepository.getTotalItemNumber()).thenReturn(0);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(0);
		when(cartProperties.getMaxUniqueItem()).thenReturn(10);
		when(cartRepository.getCart()).thenReturn(cart);
		when(cartProperties.getMaxTotalPrice()).thenReturn(500000.0);
		when(cartProperties.getDigitalItemCategoryId()).thenReturn(7889);
		when(cartRepository.hasCartDefaultItem()).thenReturn(false);
		when(cartProperties.getMaxDigitalItemQuantity()).thenReturn(5);
		when(cartRepository.getItem(addItemRequest.getItemId())).thenReturn(Optional.empty());


		when(promotionCalculator.getSuitablePromotion(cart)).thenReturn(expectedPromotion);


		//when
		cartService.addItem(addItemRequest);

		//then
		verify(cartRepository, times(1)).saveItem(addItemRequest.toDigitalItemEntity());
		verify(cartRepository, times(1))
				.updateTotalPrice(addItemRequest.getQuantity() * addItemRequest.getPrice());
		verify(cartRepository, times(1)).setPromotion(expectedPromotion);
	}

	@Test
	void it_should_add_vas_item() {
		//given
		var defaultItem = Mockito.spy(new DefaultItem(1,1001,1,1,1));

		var addVasItemRequest = new AddVasItemRequest();
		addVasItemRequest.setItemId(1);
		addVasItemRequest.setVasItemId(100);
		addVasItemRequest.setCategoryId(3242);
		addVasItemRequest.setSellerId(5003);
		addVasItemRequest.setPrice(10);
		addVasItemRequest.setQuantity(1);

		var expectedPromotion = PromotionDTO.builder()
				.promotionId(0)
				.totalDiscount(0)
				.build();

		var cart = new Cart(new ArrayList<>(), 0, 0 ,0);

		when(cartRepository.getItem(addVasItemRequest.getItemId())).thenReturn(Optional.of(defaultItem));
		when(cartProperties.getVasItemValidCategories()).thenReturn(new int[]{1001, 3004});
		when(cartProperties.getVasItemCategoryId()).thenReturn(3242);
		when(cartProperties.getVasItemSellerId()).thenReturn(5003);
		when(cartProperties.getMaxUniqueVasItem()).thenReturn(3);

		when(defaultItem.getCategoryId()).thenReturn(1001);
		when(defaultItem.getVasItems()).thenReturn(new ArrayList<>());

		when(cartRepository.getCart()).thenReturn(cart);
		when(promotionCalculator.getSuitablePromotion(cart)).thenReturn(expectedPromotion);


		//when
		cartService.addVasItem(addVasItemRequest);

		//then
		verify(defaultItem, times(1)).addVasItem(addVasItemRequest.toEntity());
		verify(cartRepository, times(1))
				.updateTotalPrice(addVasItemRequest.getQuantity() * addVasItemRequest.getPrice());
		verify(cartRepository, times(1)).setPromotion(expectedPromotion);
	}

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

	@Test
	void it_should_throw_exception_when_max_quantity_exceeded() {
		//given
		AddItemRequest addItemRequest = new AddItemRequest();
		addItemRequest.setQuantity(31);
		addItemRequest.setPrice(10);
		when(cartProperties.getMaxItem()).thenReturn(30);

		//when & then
		assertThrows(CartLimitExceededException.class, () -> cartService.addItem(addItemRequest));
	}

	@Test
	void it_should_throw_exception_when_total_item_number_exceeded() {
		//given
		AddItemRequest addItemRequest = new AddItemRequest();
		addItemRequest.setQuantity(1);
		addItemRequest.setPrice(1);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartProperties.getMaxUniqueItem()).thenReturn(1);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(2);

		//when & then
		assertThrows(CartLimitExceededException.class, () -> cartService.addItem(addItemRequest));
	}

	@Test
	void it_should_throw_exception_when_max_price_exceeded() {
		//given
		AddItemRequest addItemRequest = new AddItemRequest();
		addItemRequest.setQuantity(1);
		addItemRequest.setPrice(500001);

		var cart = new Cart(new ArrayList<>(), 0, 0 ,0);

		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartProperties.getMaxUniqueItem()).thenReturn(10);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(2);
		when(cartProperties.getMaxTotalPrice()).thenReturn(500.0);
		when(cartRepository.getCart()).thenReturn(cart);


		//when & then
		assertThrows(CartLimitExceededException.class, () -> cartService.addItem(addItemRequest));
	}


	@Test
	void it_should_throw_exception_when_adding_digital_item_to_default_item_cart() {
		//given
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(100);
		addItemRequest.setCategoryId(7889);
		addItemRequest.setSellerId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);

		var defaultItem = new DefaultItem(1,1,1,1,1);
		var cart = new Cart(Collections.singletonList(defaultItem), 10, 0 ,0);

		when(cartRepository.getTotalItemNumber()).thenReturn(0);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(0);
		when(cartProperties.getMaxUniqueItem()).thenReturn(10);
		when(cartRepository.getCart()).thenReturn(cart);
		when(cartProperties.getMaxTotalPrice()).thenReturn(500000.0);
		when(cartProperties.getDigitalItemCategoryId()).thenReturn(7889);
		when(cartRepository.hasCartDefaultItem()).thenReturn(true);


		//when & then
		assertThrows(DigitalItemCanNotAddedException.class, () -> cartService.addItem(addItemRequest));
	}

	@Test
	void it_should_throw_exception_when_adding_default_item_to_digital_item_cart() {
		//given
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(100);
		addItemRequest.setCategoryId(1);
		addItemRequest.setSellerId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);

		var digitalItem = new DigitalItem(1,7889,1,1,1);
		var cart = new Cart(Collections.singletonList(digitalItem), 10, 0 ,0);

		when(cartRepository.getTotalItemNumber()).thenReturn(0);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(0);
		when(cartProperties.getMaxUniqueItem()).thenReturn(10);
		when(cartRepository.getCart()).thenReturn(cart);
		when(cartProperties.getMaxTotalPrice()).thenReturn(500000.0);
		when(cartProperties.getDigitalItemCategoryId()).thenReturn(7889);
		when(cartRepository.hasCartDigitalItem()).thenReturn(true);


		//when & then
		assertThrows(DefaultItemCanNotAddedException.class, () -> cartService.addItem(addItemRequest));
	}

	@Test
	void it_should_throw_exception_when_adding_vas_item_to_digital_item() {
		//given
		var addVasItemRequest = new AddVasItemRequest();
		addVasItemRequest.setItemId(100);
		addVasItemRequest.setCategoryId(1);
		addVasItemRequest.setSellerId(1);
		addVasItemRequest.setPrice(10);
		addVasItemRequest.setQuantity(1);

		var digitalItem = new DigitalItem(1,7889,1,1,1);

		when(cartRepository.getItem(addVasItemRequest.getItemId())).thenReturn(Optional.of(digitalItem));


		//when & then
		assertThrows(VasItemCanNotAddedException.class, () -> cartService.addVasItem(addVasItemRequest));
	}

	@Test
	void it_should_throw_exception_when_adding_vas_item_to_wrong_category_item() {
		//given
		var addVasItemRequest = new AddVasItemRequest();
		addVasItemRequest.setItemId(100);
		addVasItemRequest.setCategoryId(1);
		addVasItemRequest.setSellerId(1);
		addVasItemRequest.setPrice(10);
		addVasItemRequest.setQuantity(1);

		var defaultItem = new DefaultItem(1,1,1,1,1);

		when(cartRepository.getItem(addVasItemRequest.getItemId())).thenReturn(Optional.of(defaultItem));
		when(cartProperties.getVasItemValidCategories()).thenReturn(new int[]{1001, 3004});



		//when & then
		assertThrows(VasItemNotValidException.class, () -> cartService.addVasItem(addVasItemRequest));
	}

	@Test
	void it_should_throw_exception_when_adding_vas_item_with_wrong_category_id() {
		//given
		var addVasItemRequest = new AddVasItemRequest();
		addVasItemRequest.setItemId(100);
		addVasItemRequest.setCategoryId(1);
		addVasItemRequest.setSellerId(1);
		addVasItemRequest.setPrice(10);
		addVasItemRequest.setQuantity(1);

		var defaultItem = new DefaultItem(1,1001,1,1,1);

		when(cartRepository.getItem(addVasItemRequest.getItemId())).thenReturn(Optional.of(defaultItem));
		when(cartProperties.getVasItemValidCategories()).thenReturn(new int[]{1001, 3004});
		when(cartProperties.getVasItemCategoryId()).thenReturn(3242);



		//when & then
		assertThrows(VasItemNotValidException.class, () -> cartService.addVasItem(addVasItemRequest));
	}

	@Test
	void it_should_throw_exception_when_adding_vas_item_with_wrong_seller_id() {
		//given
		var addVasItemRequest = new AddVasItemRequest();
		addVasItemRequest.setItemId(100);
		addVasItemRequest.setCategoryId(3242);
		addVasItemRequest.setSellerId(1);
		addVasItemRequest.setPrice(10);
		addVasItemRequest.setQuantity(1);

		var defaultItem = new DefaultItem(1,1001,1,1,1);

		when(cartRepository.getItem(addVasItemRequest.getItemId())).thenReturn(Optional.of(defaultItem));
		when(cartProperties.getVasItemValidCategories()).thenReturn(new int[]{1001, 3004});
		when(cartProperties.getVasItemCategoryId()).thenReturn(3242);
		when(cartProperties.getVasItemSellerId()).thenReturn(5003);


		//when & then
		assertThrows(VasItemNotValidException.class, () -> cartService.addVasItem(addVasItemRequest));
	}

	@Test
	void it_should_throw_exception_when_adding_vas_item_with_more_than_threshold() {
		//given
		var addVasItemRequest = new AddVasItemRequest();
		addVasItemRequest.setItemId(100);
		addVasItemRequest.setCategoryId(3242);
		addVasItemRequest.setSellerId(5003);
		addVasItemRequest.setPrice(10);
		addVasItemRequest.setQuantity(1);

		var defaultItem = Mockito.spy(new DefaultItem(1,1001,1,1,1));

		when(cartRepository.getItem(addVasItemRequest.getItemId())).thenReturn(Optional.of(defaultItem));
		when(cartProperties.getVasItemValidCategories()).thenReturn(new int[]{1001, 3004});
		when(cartProperties.getVasItemCategoryId()).thenReturn(3242);
		when(cartProperties.getVasItemSellerId()).thenReturn(5003);
		when(cartProperties.getMaxUniqueVasItem()).thenReturn(0);
		when(defaultItem.getVasItems()).thenReturn(new ArrayList<>());



		//when & then
		assertThrows(VasItemNotValidException.class, () -> cartService.addVasItem(addVasItemRequest));
	}

	@Test
	void it_should_throw_exception_when_adding_default_item_more_than_quantity() {
		//given
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setCategoryId(1);
		addItemRequest.setSellerId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(2);

		var defaultItem = new DefaultItem(1,1,1,1,9);
		var cart = new Cart(Collections.singletonList(defaultItem), 10, 0 ,0);

		when(cartRepository.getTotalItemNumber()).thenReturn(0);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(0);
		when(cartProperties.getMaxUniqueItem()).thenReturn(10);
		when(cartRepository.getCart()).thenReturn(cart);
		when(cartProperties.getMaxTotalPrice()).thenReturn(500000.0);
		when(cartProperties.getDigitalItemCategoryId()).thenReturn(7889);
		when(cartProperties.getMaxItemQuantity()).thenReturn(10);
		when(cartRepository.getItem(1)).thenReturn(Optional.of(defaultItem));

		//when & then
		assertThrows(ItemQuantityExceedException.class, () -> cartService.addItem(addItemRequest));
	}

	@Test
	void it_should_throw_exception_when_adding_digital_item_more_than_quantity() {
		//given
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setCategoryId(7889);
		addItemRequest.setSellerId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(2);

		var digitalItem = new DigitalItem(1,7889,1,1,4);
		var cart = new Cart(Collections.singletonList(digitalItem), 10, 0 ,0);

		when(cartRepository.getTotalItemNumber()).thenReturn(0);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(0);
		when(cartProperties.getMaxUniqueItem()).thenReturn(10);
		when(cartRepository.getCart()).thenReturn(cart);
		when(cartProperties.getMaxTotalPrice()).thenReturn(500000.0);
		when(cartProperties.getDigitalItemCategoryId()).thenReturn(7889);
		when(cartProperties.getMaxDigitalItemQuantity()).thenReturn(5);
		when(cartRepository.getItem(1)).thenReturn(Optional.of(digitalItem));

		//when & then
		assertThrows(ItemQuantityExceedException.class, () -> cartService.addItem(addItemRequest));
	}

	@Test
	void test_should_set_promotion_when_not_suitable_promotion_found(){
		//given
		var addItemRequest = new AddItemRequest();
		addItemRequest.setItemId(1);
		addItemRequest.setCategoryId(1);
		addItemRequest.setSellerId(1);
		addItemRequest.setPrice(10);
		addItemRequest.setQuantity(1);

		var expectedPromotion = PromotionDTO.builder()
				.promotionId(0)
				.totalDiscount(0)
				.build();

		var cart = new Cart(Collections.singletonList(addItemRequest.toDefaultItemEntity()), 10, 0 ,0);

		when(cartRepository.getTotalItemNumber()).thenReturn(0);
		when(cartProperties.getMaxItem()).thenReturn(30);
		when(cartRepository.getTotalUniqueItemNumber()).thenReturn(0);
		when(cartProperties.getMaxUniqueItem()).thenReturn(10);
		when(cartRepository.getCart()).thenReturn(cart);
		when(cartProperties.getMaxTotalPrice()).thenReturn(500000.0);
		when(cartProperties.getDigitalItemCategoryId()).thenReturn(7889);
		when(cartRepository.hasCartDigitalItem()).thenReturn(false);
		when(cartProperties.getMaxItemQuantity()).thenReturn(10);
		when(cartRepository.getItem(addItemRequest.getItemId())).thenReturn(Optional.empty());


		when(promotionCalculator.getSuitablePromotion(cart)).thenThrow(new NoSuchElementException());

		//when
		cartService.addItem(addItemRequest);

		//then
		verify(cartRepository, times(0)).setPromotion(expectedPromotion);
	}

}