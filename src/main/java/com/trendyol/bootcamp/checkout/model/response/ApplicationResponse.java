package com.trendyol.bootcamp.checkout.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApplicationResponse<T> {
	private boolean result;
	private T message;
}
