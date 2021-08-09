package com.shrikane.makespace.converter;

import com.shrikane.makespace.dto.BookRequestDTO;
import org.springframework.core.convert.converter.Converter;

public class BookRequestConverter implements Converter<String, BookRequestDTO> {
    @Override
    public BookRequestDTO convert(final String source) {
        final String[] splitInput = source.split(" ");
        return new BookRequestDTO(splitInput[1], splitInput[2], splitInput[3]);
    }
}
