package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.entity.BookedRoom;
import com.shrikane.makespace.repository.BookedRoomRepository;
import com.shrikane.makespace.service.BookRoomService;
import com.shrikane.makespace.service.CheckAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookRoomServiceImpl implements BookRoomService {

    private final BookedRoomRepository bookedRoomRepository;
    private final CheckAvailabilityService checkAvailabilityService;

    @Autowired
    public BookRoomServiceImpl(
            final BookedRoomRepository bookedRoomRepository,
            final CheckAvailabilityService checkAvailabilityService) {
        this.bookedRoomRepository = bookedRoomRepository;
        this.checkAvailabilityService = checkAvailabilityService;
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
