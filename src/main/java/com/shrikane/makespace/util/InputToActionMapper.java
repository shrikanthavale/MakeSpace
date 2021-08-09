package com.shrikane.makespace.util;

import com.shrikane.makespace.dto.ActionRequested;
import com.shrikane.makespace.exception.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public final class InputToActionMapper {
    private static final Logger LOG = LoggerFactory.getLogger(InputToActionMapper.class);
    private InputToActionMapper() {
    }

    public static ActionRequested mapInputToAction(final String inputString) throws InvalidInputException {

        if (StringUtils.isEmpty(inputString)) {
            LOG.error("ERROR : Empty line found while parsing input");
            throw new InvalidInputException("Empty line found while parsing input");
        }

        final String[] requestCommandSplit = inputString.split(" ");
        if (requestCommandSplit == null || requestCommandSplit.length == 0) {
            LOG.error("ERROR : Empty line found while parsing input");
            throw new InvalidInputException("Empty line found while parsing input");
        }

        return convertFrom(requestCommandSplit[0]);
    }

    private static ActionRequested convertFrom(final String actionRequestedString) throws InvalidInputException {
        try {
            return ActionRequested.valueOf(actionRequestedString);
        } catch (final IllegalArgumentException illegalArgumentException) {
            throw new InvalidInputException(illegalArgumentException);
        }
    }
}
