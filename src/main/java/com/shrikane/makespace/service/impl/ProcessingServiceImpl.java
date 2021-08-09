package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RequestDTO;
import com.shrikane.makespace.dto.RoomName;
import com.shrikane.makespace.dto.VacancyRequestDTO;
import com.shrikane.makespace.exception.InvalidInputException;
import com.shrikane.makespace.service.BookRoomService;
import com.shrikane.makespace.service.CheckAvailabilityService;
import com.shrikane.makespace.service.ProcessingService;
import com.shrikane.makespace.service.ReadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@Service
public class ProcessingServiceImpl implements ProcessingService {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingServiceImpl.class);
    private final ReadFileService readFileService;
    private final BookRoomService bookRoomService;
    private final CheckAvailabilityService checkAvailabilityService;

    @Autowired
    public ProcessingServiceImpl(
            final ReadFileService readFileService,
            final BookRoomService bookRoomService,
            final CheckAvailabilityServiceImpl checkAvailabilityService) {
        this.readFileService = readFileService;
        this.bookRoomService = bookRoomService;
        this.checkAvailabilityService = checkAvailabilityService;
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
                final RoomName bestRoomAvailable = checkAvailabilityService.findBestRoomAvailable((BookRequestDTO) requestDTO);
                if (bestRoomAvailable == null) {
                    finalResult.append(generateOutput(requestDTO, "NO_VACANT_ROOM"));
                } else {
                    bookRoomService.bookRoom((BookRequestDTO) requestDTO, bestRoomAvailable);
                    finalResult.append(generateOutput(requestDTO, bestRoomAvailable.name()));
                }
            } else if (requestDTO instanceof VacancyRequestDTO) {
                List<RoomName> roomNames = checkAvailabilityService.checkAvailableRooms((VacancyRequestDTO) requestDTO);
                if (CollectionUtils.isEmpty(roomNames)) {
                    finalResult.append(generateOutput(requestDTO, "NO_VACANT_ROOM"));
                } else {
                    finalResult.append(generateOutput(requestDTO, roomNames.toString()));
                }
            }
            finalResult.append(System.lineSeparator());
        }

        return finalResult.toString();
    }

    private String generateOutput(
            final RequestDTO requestDTO,
            final String result) {
        final StringBuilder input = new StringBuilder();
        final StringBuilder output = new StringBuilder();
        input.append(requestDTO.getActionRequested())
                .append(" ")
                .append(requestDTO.getStartTime())
                .append(" ")
                .append(requestDTO.getEndTime())
                .append(" ")
                .append(requestDTO instanceof BookRequestDTO ? ((BookRequestDTO) requestDTO).getPersonCapacity() : " ");
        output.append(result);
        return input.append("-->").append(" ").append(output).toString();
    }
}
