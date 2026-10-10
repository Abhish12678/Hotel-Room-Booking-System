package com.mumbaidiaries.hotel.service;

import com.mumbaidiaries.hotel.dto.BookingRequest;
import com.mumbaidiaries.hotel.dto.BookingResponse;
import com.mumbaidiaries.hotel.entity.Booking;
import com.mumbaidiaries.hotel.entity.Room;
import com.mumbaidiaries.hotel.entity.User;
import com.mumbaidiaries.hotel.exception.ResourceNotFoundException;
import com.mumbaidiaries.hotel.exception.RoomNotAvailableException;
import com.mumbaidiaries.hotel.repository.BookingRepository;
import com.mumbaidiaries.hotel.repository.RoomRepository;
import com.mumbaidiaries.hotel.repository.UserRepository;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookingService {

    private static final Set<String> ALLOWED_STATUSES =
            Set.of("Confirmed", "Checked-In", "Completed", "Cancelled");

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          RoomRepository roomRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BookingResponse create(BookingRequest request) {
        if (!request.checkOut().isAfter(request.checkIn())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        Room room = roomRepository.findAvailableRooms(request.roomType(), request.checkIn(), request.checkOut())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RoomNotAvailableException(
                        "No " + request.roomType() + " available for the selected dates"));

        User user = userRepository.findByEmail(request.email())
                .orElseGet(() -> userRepository.save(
                        new User(request.name(), request.email(), request.phone())));

        long nights = ChronoUnit.DAYS.between(request.checkIn(), request.checkOut());
        BigDecimal totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking(user, room, request.checkIn(), request.checkOut(),
                request.numberOfGuests(), totalAmount);

        return BookingResponse.from(bookingRepository.save(booking));
    }

    public List<BookingResponse> findAll() {
        return bookingRepository.findAll().stream()
                .map(BookingResponse::from)
                .toList();
    }

    public BookingResponse findById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + id));
        return BookingResponse.from(booking);
    }

    public List<BookingResponse> findByEmail(String email) {
        return bookingRepository.findByUserEmailOrderByBookingIdDesc(email).stream()
                .map(BookingResponse::from)
                .toList();
    }

    @Transactional
    public BookingResponse updateStatus(Integer id, String status) {
        if (!ALLOWED_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found: " + id));
        booking.setBookingStatus(status);
        return BookingResponse.from(booking);
    }
}
