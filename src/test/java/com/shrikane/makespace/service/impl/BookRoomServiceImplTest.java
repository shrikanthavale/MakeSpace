package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.entity.BookedRoom;
import com.shrikane.makespace.repository.BookedRoomRepository;
import com.shrikane.makespace.service.BookRoomService;
import com.shrikane.makespace.service.CheckAvailabilityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookRoomServiceImplTest {
    private BookRoomService bookRoomService;

    @Autowired
    private BookedRoomRepository bookedRoomRepository;

    @Autowired
    private CheckAvailabilityService checkAvailabilityService;

    @Before
    public void setup() {
        bookRoomService = new BookRoomServiceImpl(bookedRoomRepository, checkAvailabilityService);
    }

    @Test
    public void testBookRoom() {
        assertThat(bookedRoomRepository.findAll()).isEmpty();
        final BookRequestDTO bookRequestDTO = new BookRequestDTO("09:30", "10:30", "3");
        bookRoomService.bookRoom(bookRequestDTO, RoomName.C_CAVE);
        assertThat(bookedRoomRepository.findAll())
                .extracting(BookedRoom::getId, BookedRoom::getRoomName, BookedRoom::getStartTime, BookedRoom::getEndTime)
                .containsExactly(tuple(1L, RoomName.C_CAVE, "09:30", "10:30"));
    }
}
