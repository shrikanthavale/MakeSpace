package com.shrikane.makespace.service;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.dto.VacancyRequestDTO;

import java.util.List;

public interface CheckAvailabilityService {
    List<RoomName> checkAvailableRooms(VacancyRequestDTO vacancyRequestDTO);

    RoomName findBestRoomAvailable(BookRequestDTO bookRequestDTO);
}
