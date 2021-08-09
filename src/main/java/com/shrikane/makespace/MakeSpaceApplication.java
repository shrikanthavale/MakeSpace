package com.shrikane.makespace;

import com.shrikane.makespace.exception.InvalidInputException;
import com.shrikane.makespace.service.ProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@Import(MakeSpaceConfiguration.class)
@SpringBootApplication
public class MakeSpaceApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MakeSpaceApplication.class);

    private final ProcessingService processingService;

    @Autowired
    public MakeSpaceApplication(final ProcessingService processingService) {
        this.processingService = processingService;
    }

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(MakeSpaceApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(final String... args) {
        LOG.info("READING : The given file path");
        if (args == null || args.length == 0) {
            LOG.error("ERROR : No valid file path provided in var args");
            return;
        }

        if (args.length > 1) {
            LOG.error("ERROR : Application supports only one var args, please enter file path in quotes(\" \") if it contains spaces");
            return;
        }

        try {
            final String result = processingService.startProcessing(args[0]);
            LOG.info("Finished processing the requests from input text file");
            LOG.info("\n{}", result);
        } catch (InvalidInputException invalidInputException) {
            LOG.error("ERROR : Error occurred during processing of the input '{}'", invalidInputException.getMessage(), invalidInputException);
        } catch (final IOException ioException) {
            LOG.error("ERROR : Error occurred during reading of file '{}'", ioException.getMessage(), ioException);
        }
    }
}