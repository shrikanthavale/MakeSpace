package com.shrikane.makespace.converter;

import com.shrikane.makespace.dto.VacancyRequestDTO;
import org.springframework.core.convert.converter.Converter;

public class VacancyRequestConverter implements Converter<String, VacancyRequestDTO> {
    @Override
    public VacancyRequestDTO convert(final String source) {
        String[] s = source.split(" ");
        return new VacancyRequestDTO(s[1], s[2]);
    }
}
