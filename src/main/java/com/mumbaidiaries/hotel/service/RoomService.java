package com.mumbaidiaries.hotel.service;

import com.mumbaidiaries.hotel.dto.RoomResponse;
import com.mumbaidiaries.hotel.entity.Room;
import com.mumbaidiaries.hotel.exception.ResourceNotFoundException;
import com.mumbaidiaries.hotel.repository.RoomRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> findAll() {
        return roomRepository.findAll().stream()
                .map(RoomResponse::from)
                .toList();
    }

    public RoomResponse findById(Integer id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + id));
        return RoomResponse.from(room);
    }
}
