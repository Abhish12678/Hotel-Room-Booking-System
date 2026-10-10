package com.mumbaidiaries.hotel.repository;

import com.mumbaidiaries.hotel.entity.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUserEmailOrderByBookingIdDesc(String email);
}
