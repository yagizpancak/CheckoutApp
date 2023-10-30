package com.trendyol.bootcamp.checkout.repository;

import com.trendyol.bootcamp.checkout.model.entity.cart.Cart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;



@Repository
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
public class CartRepository {
	private Cart cart;


}
