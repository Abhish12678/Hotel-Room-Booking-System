package com.mumbaidiaries.hotel.controller;

import com.mumbaidiaries.hotel.dto.RoomResponse;
import com.mumbaidiaries.hotel.service.RoomService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomResponse> list() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public RoomResponse get(@PathVariable Integer id) {
        return roomService.findById(id);
    }
}
