package com.mumbaidiaries.hotel.dto;

import com.mumbaidiaries.hotel.entity.Booking;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponse(
        Integer bookingId,
        String guestName,
        String email,
        String roomType,
        String roomNumber,
        LocalDate checkIn,
        LocalDate checkOut,
        Integer numberOfGuests,
        BigDecimal totalAmount,
        String bookingStatus) {

    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                booking.getBookingId(),
                booking.getUser().getName(),
                booking.getUser().getEmail(),
                booking.getRoom().getRoomType(),
                booking.getRoom().getRoomNumber(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getNumberOfGuests(),
                booking.getTotalAmount(),
                booking.getBookingStatus());
    }
}
