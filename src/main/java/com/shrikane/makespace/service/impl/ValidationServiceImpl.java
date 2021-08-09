package com.shrikane.makespace.service.impl;

import com.shrikane.makespace.dto.BookRequestDTO;
import com.shrikane.makespace.dto.RequestDTO;
import com.shrikane.makespace.dto.VacancyRequestDTO;
import com.shrikane.makespace.service.ValidationService;
import com.shrikane.makespace.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    private static final Logger LOG = LoggerFactory.getLogger(ValidationServiceImpl.class);
    private static final Pattern TIME_FORMAT_PATTERN = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):([0][0]|[1][5]|[3][0]|[4][5])$", Pattern.DOTALL);

    @Override
    public boolean validate(BookRequestDTO bookRequestDTO) {

        // person capacity validation
        final String personCapacity = bookRequestDTO.getPersonCapacity();
        try {
            Integer.parseInt(personCapacity);
        } catch (final NumberFormatException numberFormatException) {
            LOG.error("ERROR : Invalid person capacity entered");
            return false;
        }

        return validateTimeFormats(bookRequestDTO);
    }


    @Override
    public boolean validate(final VacancyRequestDTO vacancyRequestDTO) {
        return validateTimeFormats(vacancyRequestDTO);
    }

    private boolean validateTimeFormats(final RequestDTO requestDTO) {
        final String startTimeString = requestDTO.getStartTime();
        if (!TIME_FORMAT_PATTERN.matcher(startTimeString).find()) {
            LOG.error("ERROR : Start time is in invalid format, it should be in HH:mm and of 15 minutes interval");
            return false;
        }

        final String endTimeString = requestDTO.getEndTime();
        if (!TIME_FORMAT_PATTERN.matcher(endTimeString).find()) {
            LOG.error("ERROR : End time is in invalid format, it should be in HH:mm and of 15 minutes interval");
            return false;
        }

        final LocalDateTime startTime = TimeUtil.toLocalDate(startTimeString);
        final LocalDateTime endTime = TimeUtil.toLocalDate(endTimeString);
        if (startTime.isAfter(endTime)) {
            LOG.error("ERROR : Start time should be before end time");
            return false;
        }

        return true;
    }
}
