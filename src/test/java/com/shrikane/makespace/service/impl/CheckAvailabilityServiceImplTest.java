package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.dto.VacancyRequestDTO;
import com.shrikane.makespace.repository.BookedRoomRepository;
import com.shrikane.makespace.service.BookRoomService;
import com.shrikane.makespace.service.CheckAvailabilityService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CheckAvailabilityServiceImplTest {
    private CheckAvailabilityService checkAvailabilityService;

    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Autowired
    private BookRoomService bookRoomService;

    @Before
    public void setup() {
        checkAvailabilityService = new CheckAvailabilityServiceImpl(bookedRoomRepository);
    }

    @Test
    public void testCheckAvailableRooms() {
        // case 0 : room requested during buffer time
        assertThat(bookedRoomRepository.findAll()).isEmpty();
        assertThat(checkAvailabilityService.checkAvailableRooms(new VacancyRequestDTO("09:00", "09:15"))).isNullOrEmpty();
        assertThat(checkAvailabilityService.checkAvailableRooms(new VacancyRequestDTO("13:15", "13:45"))).isNullOrEmpty();
        assertThat(checkAvailabilityService.checkAvailableRooms(new VacancyRequestDTO("18:45", "19:00"))).isNullOrEmpty();

        // case 1 : no rooms reserved at all
        assertThat(bookedRoomRepository.findAll()).isEmpty();
        assertThat(checkAvailabilityService.checkAvailableRooms(new VacancyRequestDTO("10:00", "12:00"))).containsExactly(RoomName.values());

        // case 2 : book one room
        bookRoomService.bookRoom(new BookRequestDTO("11:00", "11:45", "2"), RoomName.C_CAVE);
        assertThat(checkAvailabilityService.checkAvailableRooms(new VacancyRequestDTO("10:00", "12:00")))
                .containsExactly(RoomName.D_TOWER, RoomName.G_MANSION);

        // case 3 : book two rooms
        bookRoomService.bookRoom(new BookRequestDTO("11:30", "13:00", "15"), RoomName.G_MANSION);
        assertThat(checkAvailabilityService.checkAvailableRooms(new VacancyRequestDTO("11:30", "12:00")))
                .containsExactly(RoomName.D_TOWER);

        // case 4 : no rooms available
        bookRoomService.bookRoom(new BookRequestDTO("11:30", "13:00", "6"), RoomName.D_TOWER);
        assertThat(checkAvailabilityService.checkAvailableRooms(new VacancyRequestDTO("11:30", "12:00"))).isNullOrEmpty();
    }

    @Test
    public void testFindBestRoomAvailable() {
        // case 0 : room requested during buffer time
        assertThat(bookedRoomRepository.findAll()).isEmpty();
        assertThat(checkAvailabilityService.findBestRoomAvailable(new BookRequestDTO("09:00", "09:15", "2"))).isNull();
        assertThat(checkAvailabilityService.findBestRoomAvailable(new BookRequestDTO("13:15", "13:45", "3"))).isNull();
        assertThat(checkAvailabilityService.findBestRoomAvailable(new BookRequestDTO("18:45", "19:00", "5"))).isNull();

        // case 1 : no rooms reserved at all
        assertThat(bookedRoomRepository.findAll()).isEmpty();
        assertThat(checkAvailabilityService.findBestRoomAvailable(new BookRequestDTO("11:00", "11:45", "2"))).isEqualTo(RoomName.C_CAVE);

        // case 2 : book one room
        bookRoomService.bookRoom(new BookRequestDTO("11:00", "11:45", "2"), RoomName.C_CAVE);
        assertThat(checkAvailabilityService.findBestRoomAvailable(new BookRequestDTO("11:30", "13:00", "6"))).isEqualTo(RoomName.D_TOWER);

        // case 3 : book two rooms
        bookRoomService.bookRoom(new BookRequestDTO("11:30", "13:00", "6"), RoomName.D_TOWER);
        assertThat(checkAvailabilityService.findBestRoomAvailable(new BookRequestDTO("11:30", "13:00", "2"))).isEqualTo(RoomName.G_MANSION);

        // case 4 : no rooms available
        bookRoomService.bookRoom(new BookRequestDTO("11:30", "13:00", "19"), RoomName.G_MANSION);
        assertThat(checkAvailabilityService.findBestRoomAvailable(new BookRequestDTO("11:15", "13:00", "2"))).isNull();
    }

    @After
    public void tearDown() {
        bookedRoomRepository.deleteAll();
    }
}
