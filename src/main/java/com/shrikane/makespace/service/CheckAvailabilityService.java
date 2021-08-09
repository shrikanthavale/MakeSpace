package com.shrikane.makespace.service;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.dto.VacancyRequestDTO;
import com.shrikane.makespace.util.TimeUtil;

import java.time.LocalDateTime;
import java.util.List;

public interface CheckAvailabilityService {

    LocalDateTime BUFFER_TIME_1_START = TimeUtil.toLocalDate("09:00");
    LocalDateTime BUFFER_TIME_1_END = TimeUtil.toLocalDate("09:15");
    LocalDateTime BUFFER_TIME_2_START = TimeUtil.toLocalDate("13:15");
    LocalDateTime BUFFER_TIME_2_END = TimeUtil.toLocalDate("13:45");
    LocalDateTime BUFFER_TIME_3_START = TimeUtil.toLocalDate("18:45");
    LocalDateTime BUFFER_TIME_3_END = TimeUtil.toLocalDate("19:00");

    List<RoomName> checkAvailableRooms(VacancyRequestDTO vacancyRequestDTO);

    RoomName findBestRoomAvailable(BookRequestDTO bookRequestDTO);
}
