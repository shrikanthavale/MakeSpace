package com.shrikane.makespace.service;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;

public interface BookRoomService {
    void bookRoom(final BookRequestDTO bookRequestDTO, final RoomName roomName);
}
