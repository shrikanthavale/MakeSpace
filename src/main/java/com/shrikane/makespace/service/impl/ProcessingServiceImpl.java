package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.dto.VacancyRequestDTO;
import com.shrikane.makespace.exception.InvalidInputException;
import com.shrikane.makespace.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessingServiceImpl implements ProcessingService {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingServiceImpl.class);
    private final ReadFileService readFileService;
    private final BookRoomService bookRoomService;
    private final CheckAvailabilityService checkAvailabilityService;
    private final ValidationService validationService;

    @Autowired
    public ProcessingServiceImpl(
            final ReadFileService readFileService,
            final BookRoomService bookRoomService,
            final CheckAvailabilityServiceImpl checkAvailabilityService,
            final ValidationService validationService) {
        this.readFileService = readFileService;
        this.bookRoomService = bookRoomService;
        this.checkAvailabilityService = checkAvailabilityService;
        this.validationService = validationService;
    }

    @Override
    public String startProcessing(String filePath) throws InvalidInputException, IOException {
        final List<RequestDTO> processedInputs = readFileService.readFileAndParseRequests(filePath);

        if (CollectionUtils.isEmpty(processedInputs)) {
            LOG.error("ERROR : No valid inputs present.");
            return null;
        }

        final StringBuilder finalResult = new StringBuilder();
        for (final RequestDTO requestDTO : processedInputs) {
            if (requestDTO instanceof BookRequestDTO) {
                final BookRequestDTO bookRequest = (BookRequestDTO) requestDTO;

                if (!validationService.validate(bookRequest)) {
                    finalResult.append(generateOutput(requestDTO, "INCORRECT_INPUT"));
                    continue;
                }

                final RoomName bestRoomAvailable = checkAvailabilityService.findBestRoomAvailable(bookRequest);
                if (bestRoomAvailable == null) {
                    finalResult.append(generateOutput(requestDTO, "NO_VACANT_ROOM"));
                } else {
                    bookRoomService.bookRoom(bookRequest, bestRoomAvailable);
                    finalResult.append(generateOutput(requestDTO, bestRoomAvailable.getName()));
                }
            } else if (requestDTO instanceof VacancyRequestDTO) {

                final VacancyRequestDTO vacancyRequest = (VacancyRequestDTO) requestDTO;
                if (!validationService.validate(vacancyRequest)) {
                    finalResult.append(generateOutput(requestDTO, "INCORRECT_INPUT"));
                    continue;
                }

                List<RoomName> roomNames = checkAvailabilityService.checkAvailableRooms(vacancyRequest);
                if (CollectionUtils.isEmpty(roomNames)) {
                    finalResult.append(generateOutput(requestDTO, "NO_VACANT_ROOM"));
                } else {
                    finalResult.append(generateOutput(requestDTO, roomNames.stream().map(RoomName::getName).collect(Collectors.joining(" "))));
                }
            }
        }

        return finalResult.toString();
    }

    private String generateOutput(
            final RequestDTO requestDTO,
            final String result) {
        final StringBuilder input = new StringBuilder();
        final StringBuilder output = new StringBuilder();
        input.append(String.format("%-10s  %s  %s", requestDTO.getActionRequested(), requestDTO.getStartTime(), requestDTO.getEndTime()))
                .append(String.format("  %-5s", requestDTO instanceof BookRequestDTO ? ((BookRequestDTO) requestDTO).getPersonCapacity() : ""));
        output.append(result);
        return input.append(String.format("%-5s","-->")).append(output).append(System.lineSeparator()).toString();
    }
}
