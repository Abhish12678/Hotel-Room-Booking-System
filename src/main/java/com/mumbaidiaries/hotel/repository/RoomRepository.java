package com.mumbaidiaries.hotel.repository;

import com.mumbaidiaries.hotel.entity.Room;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByRoomType(String roomType);

    boolean existsByRoomNumber(String roomNumber);

    @Query("""
            select r from Room r
            where r.roomType = :roomType
              and r.status = 'Available'
              and not exists (
                  select b from Booking b
                  where b.room = r
                    and b.bookingStatus = 'Confirmed'
                    and :checkIn < b.checkOut
                    and :checkOut > b.checkIn
              )
            order by r.roomNumber
            """)
    List<Room> findAvailableRooms(@Param("roomType") String roomType,
                                  @Param("checkIn") LocalDate checkIn,
                                  @Param("checkOut") LocalDate checkOut);
}
