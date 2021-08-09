package com.shrikane.makespace.service;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.VacancyRequestDTO;

public interface ValidationService {
    boolean validate(BookRequestDTO bookRequestDTO);
    boolean validate(VacancyRequestDTO vacancyRequestDTO);
}
