package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.repository.BookedRoomRepository;
import com.shrikane.makespace.service.BookRoomService;
import com.shrikane.makespace.service.CheckAvailabilityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
    }
}
