package com.shrikane.makespace.service;

import com.shrikane.makespace.dto.RequestDTO;
import com.shrikane.makespace.exception.InvalidInputException;

import java.io.IOException;
import java.util.List;

public interface ReadFileService {
    List<RequestDTO> readFileAndParseRequests(final String filePath) throws IOException, InvalidInputException;
}
