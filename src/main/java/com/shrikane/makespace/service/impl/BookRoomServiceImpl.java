package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.entity.BookedRoom;
import com.shrikane.makespace.repository.BookedRoomRepository;
import com.shrikane.makespace.service.BookRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookRoomServiceImpl implements BookRoomService {

    private final BookedRoomRepository bookedRoomRepository;

    @Autowired
    public BookRoomServiceImpl(final BookedRoomRepository bookedRoomRepository) {
        this.bookedRoomRepository = bookedRoomRepository;
    }

    @Override
    public void bookRoom(final BookRequestDTO bookRequestDTO, final RoomName roomName) {
        final BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setRoomName(roomName);
        bookedRoom.setStartTime(bookRequestDTO.getStartTime());
        bookedRoom.setEndTime(bookRequestDTO.getEndTime());
        bookedRoomRepository.save(bookedRoom);
    }
}
