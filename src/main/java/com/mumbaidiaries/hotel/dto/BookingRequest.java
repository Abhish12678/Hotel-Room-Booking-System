package com.mumbaidiaries.hotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record BookingRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @Pattern(regexp = "^$|^[0-9]{7,15}$", message = "Phone must be 7 to 15 digits")
        String phone,

        @NotNull(message = "Check-in date is required")
        LocalDate checkIn,

        @NotNull(message = "Check-out date is required")
        LocalDate checkOut,

        @NotBlank(message = "Room type is required")
        String roomType,

        @NotNull(message = "Number of guests is required")
        @Min(value = 1, message = "At least 1 guest is required")
        @Max(value = 10, message = "At most 10 guests are allowed")
        Integer numberOfGuests) {
}
