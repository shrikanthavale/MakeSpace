package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.entity.BookedRoom;
import com.shrikane.makespace.repository.BookedRoomRepository;
import com.shrikane.makespace.service.BookRoomService;
import com.shrikane.makespace.service.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ValidationServiceImplTest {
    private ValidationService validationService;

    @Before
    public void setup() {
        validationService = new ValidationServiceImpl();
    }

    @Test
    public void testValidateBookRoomRequest() {
        assertThat(validationService.validate(new BookRequestDTO("09:00", "10:00", "ab"))).isFalse();
        assertThat(validationService.validate(new BookRequestDTO("09:05", "10:00", "15"))).isFalse();
        assertThat(validationService.validate(new BookRequestDTO("09:00", "10:05", "15"))).isFalse();
        assertThat(validationService.validate(new BookRequestDTO("09:03", "10:00", "15"))).isFalse();
        assertThat(validationService.validate(new BookRequestDTO("10:00", "09:00", "15"))).isFalse();
        assertThat(validationService.validate(new BookRequestDTO("23:00", "01:00", "15"))).isFalse();
        assertThat(validationService.validate(new BookRequestDTO("09:00", "10:00", "15"))).isTrue();
    }
}

