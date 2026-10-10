package com.mumbaidiaries.hotel.config;

import com.mumbaidiaries.hotel.entity.Room;
import com.mumbaidiaries.hotel.repository.RoomRepository;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner seedRooms(RoomRepository roomRepository) {
        return args -> {
            if (roomRepository.count() > 0) {
                return;
            }

            List<Room> rooms = List.of(
                    new Room("101", "Single Bed Room", new BigDecimal("1500.00")),
                    new Room("102", "Single Bed Room", new BigDecimal("1500.00")),
                    new Room("201", "Double Bed Room", new BigDecimal("2500.00")),
                    new Room("202", "Double Bed Room", new BigDecimal("2500.00")),
                    new Room("301", "Deluxe Room", new BigDecimal("4000.00")),
                    new Room("302", "Deluxe Room", new BigDecimal("4000.00")),
                    new Room("401", "Suite Room", new BigDecimal("5000.00")));

            roomRepository.saveAll(rooms);
            log.info("Seeded {} hotel rooms", rooms.size());
        };
    }
}
