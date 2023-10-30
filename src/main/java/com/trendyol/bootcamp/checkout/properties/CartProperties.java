package com.trendyol.bootcamp.checkout.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cart")
@Data
public class CartProperties {
	private int digitalItemCategoryId;
	private double maxTotalPrice;
	private int maxItem;
	private int maxUniqueItem;
	private int maxItemQuantity;
	private int maxDigitalItemQuantity;
	private int[] vasItemValidCategories;
	private int vasItemCategoryId;
	private int vasItemSellerId;
	private int maxUniqueVasItem;
}
