package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.ActionRequested;
import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RequestDTO;
import com.shrikane.makespace.dto.VacancyRequestDTO;
import com.shrikane.makespace.exception.InvalidInputException;
import com.shrikane.makespace.service.ReadFileService;
import com.shrikane.makespace.util.InputToActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadFileServiceImpl implements ReadFileService {

    private static final Logger LOG = LoggerFactory.getLogger(ReadFileServiceImpl.class);

    private final ConversionService conversionService;

    @Autowired
    public ReadFileServiceImpl(final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public List<RequestDTO> readFileAndParseRequests(String filePath) throws IOException, InvalidInputException {
        LOG.info("READING : Attempting to read text file from input '{}'", filePath);

        if (StringUtils.isEmpty(filePath)) {
            LOG.error("ERROR : Error occurred in reading file as provided file path '{}' is null or empty", filePath);
            throw new InvalidInputException("File path null or empty");
        }

        final List<RequestDTO> requestDTOs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                final ActionRequested actionRequested = InputToActionMapper.mapInputToAction(line);
                if (actionRequested == ActionRequested.BOOK) {
                    requestDTOs.add(conversionService.convert(line, BookRequestDTO.class));
                } else if (actionRequested == ActionRequested.VACANCY) {
                    requestDTOs.add(conversionService.convert(line, VacancyRequestDTO.class));
                } else {
                    LOG.error("ERROR : Invalid action found at line '{}'", line);
                    throw new InvalidInputException(String.format("Invalid action found at line '%s'", line));
                }
            }
        }

        LOG.info("READING : Successfully read the input");
        return requestDTOs;
    }
}
