package com.shrikane.makespace.converter;

import com.shrikane.makespace.dto.BookRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

public class BookRequestConverter implements Converter<String, BookRequestDTO> {
    private static final Logger LOG = LoggerFactory.getLogger(BookRequestConverter.class);

    @Override
    public BookRequestDTO convert(final String source) {
        final String[] splitInput = source.split(" ");
        return new BookRequestDTO(splitInput[1], splitInput[2], splitInput[3]);
    }
}
