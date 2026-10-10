package com.mumbaidiaries.hotel.repository;

import com.mumbaidiaries.hotel.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
