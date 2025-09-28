package com.luizbarros.dscommerce.dto;

import java.time.Instant;

import com.luizbarros.dscommerce.entities.Payment;

public record PaymentDTO(Long id, Instant moment) {
	public PaymentDTO(Payment entity) {
		this(entity.getId(), entity.getMoment());
	}
}
