package com.example.hotelbooking.services.impl;

import com.example.hotelbooking.model.AuthEvent;
import com.example.hotelbooking.model.BookingEvent;
import com.example.hotelbooking.repository.AuthRepository;
import com.example.hotelbooking.repository.BookingEventRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatisticService {
    private final AuthRepository authRepository;
    private final BookingEventRepository bookingEventRepository;

    public void createBookingCsv() {
        String [] fileHeader = {"id", "bookingUser", "checkInDate", "checkOutDate"};
        List<BookingEvent> bookingList =  bookingEventRepository.findAll();

        List<List<String>> formattedEvents = new ArrayList<>();
        for (BookingEvent item: bookingList) {
            List<String> dataList = new ArrayList();
            dataList.add(item.getId());
            dataList.add(item.getBookingUser().toString());
            dataList.add(item.getCheckInDate().toString());
            dataList.add(item.getCheckOutDate().toString());
            formattedEvents.add(dataList);
        }

        createFile("BookingEvents", fileHeader, formattedEvents);
    }

    public void createAuthCsv() {
        String [] fileHeader = {"id", "userId"};
        List<AuthEvent> authList =  authRepository.findAll();

        List<List<String>> formattedEvents = new ArrayList<>();
        for (AuthEvent item: authList) {
            List<String> dataList = new ArrayList();
            dataList.add(item.getId());
            dataList.add(item.getUserId().toString());
            formattedEvents.add(dataList);
        }

        createFile("AuthEvents", fileHeader, formattedEvents);
    }


    public void createFile(String fileName, String[] fileHeader, List<List<String>> list) {
        Writer writer = null;
        CSVPrinter csvPrinter = null;

        try {
            writer = Files.newBufferedWriter(Paths.get(fileName + ".csv"));
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                    .setDelimiter(';')
                    .setQuote('"')
                    .setRecordSeparator("\r\n")
                    .setHeader(fileHeader).build());

            csvPrinter.printRecords(list);
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
