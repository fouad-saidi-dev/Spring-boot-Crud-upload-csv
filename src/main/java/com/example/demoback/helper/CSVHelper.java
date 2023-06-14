package com.example.demoback.helper;

import com.example.demoback.entities.UserEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {


    public static String TYPE = "text/csv";
    static String[] HEADERS = { "Id","Firstname", "Lastname", "Email", "Password" };

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<UserEntity> csvToUsers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<UserEntity> users = new ArrayList<UserEntity>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                UserEntity user = new UserEntity(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("Firstname"),
                        csvRecord.get("Lastname"),
                        csvRecord.get("Email"),
                        csvRecord.get("Password")
                );

                users.add(user);
            }

            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
