package com.crypto.streams;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvHandler {

    public static void main(String[] args) {
        String csvFilePath = "merged-csv-files.csv";

        try {
            List<List<String>> dataList = readCsvFile(csvFilePath);
            LocalDate startDate = LocalDate.of(2020, 10, 8);
            LocalDate endDate = LocalDate.of(2020, 10, 14);

            /*// Goal 1: Filter by Date Range
            System.out.println("Goal 1: Filter by Date Range");
            List<List<String>> filteredByDate = filterByDateRange(dataList, startDate, endDate);
            printData(filteredByDate);

            // Goal 2: Find Maximum Closing Price
            System.out.println("\nGoal 2: Find Maximum Closing Price");
            List<String> maxClosingPriceRow = findMaxClosingPrice(dataList);
            System.out.println("Row with Max Closing Price: " + maxClosingPriceRow);

            // Goal 3: Calculate Average Volume
            System.out.println("\nGoal 3: Calculate Average Volume");
            double averageVolume = calculateAverageVolume(dataList);
            System.out.println("Average Volume: " + averageVolume);

            // Goal 4: Filter by Market Cap Threshold
            System.out.println("\nGoal 4: Filter by Market Cap Threshold");
            double marketCapThreshold = 200000000.0;
            List<List<String>> highMarketCapRows = filterByMarketCap(dataList, marketCapThreshold);
            printData(highMarketCapRows);*/
            
         // Goal 1: Filter by Symbol
            System.out.println("\nGoal 1: Filter by Symbol");
            String targetSymbol = "AAVE"; // Replace with your desired symbol
            List<List<String>> filteredBySymbol = filterBySymbol(dataList, targetSymbol);
            printData(filteredBySymbol);

            // Goal 2: Find Minimum Closing Price
            System.out.println("\nGoal 2: Find Minimum Closing Price");
            List<String> minClosingPriceRow = findMinClosingPriceRow(dataList);
            System.out.println("Row with Min Closing Price: " + minClosingPriceRow);

            // Goal 3: Calculate Total Market Cap
            System.out.println("\nGoal 3: Calculate Total Market Cap");
            double totalMarketCap = calculateTotalMarketCap(dataList);
            System.out.println("Total Market Cap: " + totalMarketCap);

            // Goal 4: Find Top 5 Rows by Low Price
            System.out.println("\nGoal 4: Find Top N Rows by Low Price");
            int topNLowPrice = 3; // Replace with your desired value
            List<List<String>> topLowPriceRows = findTopNLowPriceRows(dataList, topNLowPrice);
            printData(topLowPriceRows);

            // Goal 5: Find Top 5 Rows by High Price
            System.out.println("\nGoal 5: Find Top N Rows by High Price");
            int topN = 5;
            List<List<String>> topHighPriceRows = findTopNHighPriceRows(dataList, topN);
            printData(topHighPriceRows);

            // Goal 6: Group by Name and Calculate Average Closing Price
            System.out.println("\nGoal 6: Group by Name and Calculate Average Closing Price");
            Map<String, Double> averageClosingBySymbol = groupAndCalculateAverageClosing(dataList);
            averageClosingBySymbol.forEach((symbol, averageClosing) ->
                    System.out.println("Symbol: " + symbol + ", Average Closing Price: " + averageClosing));

            // Perform additional processing or output results as needed
            // ...

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
 // Goal 1: Filter by Symbol
    private static List<List<String>> filterBySymbol(List<List<String>> dataList, String symbol) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> row.size() >= 3 && row.get(2).equalsIgnoreCase(symbol)) // Assuming symbol is at index 2
                .collect(Collectors.toList());
    }

    // Goal 2: Find Minimum Closing Price
    private static List<String> findMinClosingPriceRow(List<List<String>> dataList) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> row.size() >= 8 && isNumeric(row.get(7))) // Assuming close price is at index 7
                .min(Comparator.comparingDouble(row -> Double.parseDouble(row.get(7)))) // Assuming close price is at index 7
                .orElse(null);
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    // Goal 3: Calculate Total Market Cap
    private static double calculateTotalMarketCap(List<List<String>> dataList) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> row.size() >= 10 && isNumeric(row.get(9))) // Assuming market cap is at index 9
                .mapToDouble(row -> Double.parseDouble(row.get(9))) // Assuming market cap is at index 9
                .sum();
    }


    // Goal 4: Find Top 5 Rows by Low Price
    private static List<List<String>> findTopNLowPriceRows(List<List<String>> dataList, int topN) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> row.size() >= 5 && isNumeric(row.get(4))) // Assuming low price is at index 4
                .sorted(Comparator.comparingDouble(row -> Double.parseDouble(row.get(4)))) // Assuming low price is at index 4
                .limit(topN)
                .collect(Collectors.toList());
    }


    private static List<List<String>> readCsvFile(String filePath) throws IOException, CsvException {
        List<List<String>> dataList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();

            for (String[] row : rows) {
                List<String> rowData = new ArrayList<>(List.of(row));
                dataList.add(rowData);
            }
        }

        return dataList;
    }

    private static List<List<String>> filterByDateRange(List<List<String>> dataList, LocalDate startDate, LocalDate endDate) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> {
                    if (row.size() >= 4) { // Check if the row has at least 4 elements
                        try {
                            LocalDate rowDate = LocalDate.parse(row.get(3)); // Assuming date is at index 3
                            return !rowDate.isBefore(startDate) && !rowDate.isAfter(endDate);
                        } catch (DateTimeParseException e) {
                            // Handle the case where date parsing fails
                            return false;
                        }
                    }
                    return false; // Return false if the row doesn't have enough elements
                })
                .collect(Collectors.toList());
    }

    private static List<String> findMaxClosingPrice(List<List<String>> dataList) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> {
                    if (row.size() >= 8) { // Assuming close price is at index 7
                        try {
                            Double.parseDouble(row.get(7)); // Assuming close price is at index 7
                            return true;
                        } catch (NumberFormatException e) {
                            // Handle the case where parsing fails (non-numeric value)
                            return false;
                        }
                    }
                    return false; // Return false if the row doesn't have enough elements
                })
                .max(Comparator.comparingDouble(row -> Double.parseDouble(row.get(7)))) // Assuming close price is at index 7
                .orElse(null);
    }

    private static double calculateAverageVolume(List<List<String>> dataList) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> {
                    if (row.size() >= 9) { // Assuming volume is at index 8
                        try {
                            Double.parseDouble(row.get(8)); // Assuming volume is at index 8
                            return true;
                        } catch (NumberFormatException e) {
                            // Handle the case where parsing fails (non-numeric value)
                            return false;
                        }
                    }
                    return false; // Return false if the row doesn't have enough elements
                })
                .mapToDouble(row -> Double.parseDouble(row.get(8))) // Assuming volume is at index 8
                .average()
                .orElse(0.0);
    }

    private static List<List<String>> filterByMarketCap(List<List<String>> dataList, double marketCapThreshold) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> {
                    if (row.size() >= 10) { // Assuming market cap is at index 9
                        try {
                            Double.parseDouble(row.get(9)); // Assuming market cap is at index 9
                            return true;
                        } catch (NumberFormatException e) {
                            // Handle the case where parsing fails (non-numeric value)
                            return false;
                        }
                    }
                    return false; // Return false if the row doesn't have enough elements
                })
                .filter(row -> Double.parseDouble(row.get(9)) > marketCapThreshold) // Assuming market cap is at index 9
                .collect(Collectors.toList());
    }

    private static List<List<String>> findTopNHighPriceRows(List<List<String>> dataList, int topN) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> {
                    if (row.size() >= 5) { // Assuming high price is at index 4
                        try {
                            Double.parseDouble(row.get(4)); // Assuming high price is at index 4
                            return true;
                        } catch (NumberFormatException e) {
                            // Handle the case where parsing fails (non-numeric value)
                            return false;
                        }
                    }
                    return false; // Return false if the row doesn't have enough elements
                })
                .sorted(Comparator.comparingDouble(row -> Double.parseDouble(row.get(4)))) // Assuming high price is at index 4
                .limit(topN)
                .collect(Collectors.toList());
    }

    private static Map<String, Double> groupAndCalculateAverageClosing(List<List<String>> dataList) {
        return dataList.stream()
                .skip(1) // Skip the first row (assuming it contains headers)
                .filter(row -> {
                    if (row.size() >= 8) { // Assuming close price is at index 7
                        try {
                            Double.parseDouble(row.get(7)); // Assuming close price is at index 7
                            return true;
                        } catch (NumberFormatException e) {
                            // Handle the case where parsing fails (non-numeric value)
                            return false;
                        }
                    }
                    return false; // Return false if the row doesn't have enough elements
                })
                .collect(Collectors.groupingBy(row -> row.get(1), // Assuming 'Name' is at index 1
                        Collectors.averagingDouble(row -> Double.parseDouble(row.get(7))))); // Assuming close price is at index 7
    }

    private static void printData(List<List<String>> data) {
        data.forEach(row -> System.out.println(String.join(", ", row)));
    }
}
