package com.shrikane.makespace.service;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.entity.BookedRoom;

import java.util.List;

public interface BookRoomService {
    void bookRoom(final BookRequestDTO bookRequestDTO, final RoomName roomName);

    List<BookedRoom> fetchBookedRooms(final RoomName roomName);
}
