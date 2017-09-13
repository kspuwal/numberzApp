package com.numberzApp.genericLib;

import com.csvreader.CsvReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.testng.Assert.assertTrue;


public class CsvUtils {

        public static Object[][] getTestData(String filePath/*, String operationType*/) throws IOException {
            // Initialize CsvReader instance and set delimiter.
            CsvReader csvReader = new CsvReader(new FileReader(new File(filePath)));
            csvReader.setDelimiter(',');

            // Read headers.
            csvReader.readHeaders();
            String[] headers = csvReader.getHeaders();

            // Read values in rows.
            List<HashMap<String, String>> rowsData = new ArrayList<HashMap<String, String>>();
            while (csvReader.readRecord()) {
                HashMap<String, String> row = new LinkedHashMap<String, String>();
                // if (csvReader.get("action(mandatory)").equalsIgnoreCase(operationType)) {
                for (String header : headers) {
                    // Put read rows value into a map.
                    row.put(header, csvReader.get(header));
                }
                // Add populated map to the list.
                rowsData.add(row);
                //  }
            }
            assertTrue(rowsData.size() > 0, "No Test data found!!!");

            // Convert HashMap list into two dimensional array.
            Object[][] returnObject = new Object[rowsData.size()][headers.length];
            for (int itemIndex = 0; itemIndex < rowsData.size(); itemIndex++) {
                for (int headerIndex = 0; headerIndex < headers.length; headerIndex++) {
                    returnObject[itemIndex][headerIndex] = rowsData.get(itemIndex).get(headers[headerIndex]);
                }
            }
            return returnObject;
        }
    }


