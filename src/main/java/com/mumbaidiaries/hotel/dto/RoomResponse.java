package com.mumbaidiaries.hotel.dto;

import com.mumbaidiaries.hotel.entity.Room;
import java.math.BigDecimal;

public record RoomResponse(
        Integer roomId,
        String roomNumber,
        String roomType,
        BigDecimal pricePerNight,
        String status) {

    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getRoomId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPricePerNight(),
                room.getStatus());
    }
}
