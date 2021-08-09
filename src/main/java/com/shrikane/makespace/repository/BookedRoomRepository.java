package com.shrikane.makespace.repository;

import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.entity.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {
    List<BookedRoom> findByRoomName(final RoomName roomName);
}
