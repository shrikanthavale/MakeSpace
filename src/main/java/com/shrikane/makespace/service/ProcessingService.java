package com.shrikane.makespace.service;

import com.shrikane.makespace.exception.InvalidInputException;

import java.io.IOException;

public interface ProcessingService {
    String startProcessing(final String filePath) throws InvalidInputException, IOException;
}
