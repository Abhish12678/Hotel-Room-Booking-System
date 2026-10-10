package com.mumbaidiaries.hotel.controller;

import com.mumbaidiaries.hotel.dto.BookingRequest;
import com.mumbaidiaries.hotel.dto.BookingResponse;
import com.mumbaidiaries.hotel.service.BookingService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody BookingRequest request) {
        return bookingService.create(request);
    }

    @GetMapping
    public List<BookingResponse> list(@RequestParam(required = false) String email) {
        return email == null ? bookingService.findAll() : bookingService.findByEmail(email);
    }

    @GetMapping("/{id}")
    public BookingResponse get(@PathVariable Integer id) {
        return bookingService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public BookingResponse updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return bookingService.updateStatus(id, status);
    }
}
