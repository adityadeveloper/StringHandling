package com.configurations;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class OpenCSVReader {
	public static void main(String[] args) {

        String csvFile = "wifi_ap_141020161848.csv";

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println("[" + line[0] + ",\n" + line[1] + ",\n" + line[3]+ ",\n" + line[4]+ ",\n" + line[5]+ ",\n" + line[6] + "]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
