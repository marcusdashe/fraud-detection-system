package io.marcus;


import io.marcus.model.TransactionEvent;
import io.marcus.service.FraudDetectionService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a  FraudDetectionService instance
        FraudDetectionService fraudDetection = new FraudDetectionService();

        // Get the test dataset
        List<TransactionEvent> transactions = createTestOutOfOrderDataset();

        for (TransactionEvent event : transactions) {
             fraudDetection.processTransactionEvent(event);
        }
    }

    // Method to create the test dataset
    private static List<TransactionEvent> createTestOutOfOrderDataset() {

        List<TransactionEvent> outOfOrderEvents = new ArrayList<>();

//        input dataset from instructions
            outOfOrderEvents.add(new TransactionEvent(1617906000, 150.00, "user1", "serviceA"));
            outOfOrderEvents.add(new TransactionEvent(1617906060, 4500.00, "user2", "serviceB"));
            outOfOrderEvents.add(new TransactionEvent(1617906120, 75.00, "user1", "serviceC"));
            outOfOrderEvents.add(new TransactionEvent(1617906180, 3000.00, "user3", "serviceA"));
            outOfOrderEvents.add(new TransactionEvent(1617906240, 200.00, "user1", "serviceB"));
            outOfOrderEvents.add(new TransactionEvent(1617906300, 4800.00, "user2", "serviceC"));
            outOfOrderEvents.add(new TransactionEvent(1617906360, 100.00, "user4", "serviceA"));
            outOfOrderEvents.add(new TransactionEvent(1617906420, 4900.00, "user3", "serviceB"));
            outOfOrderEvents.add(new TransactionEvent(1617906480, 120.00, "user1", "serviceD"));
            outOfOrderEvents.add(new TransactionEvent(1617906540, 5000.00, "user3", "serviceC"));


//        outOfOrderEvents.add(new TransactionEvent(1617906000, 150.00, "user1", "serviceA"));
//        outOfOrderEvents.add(new TransactionEvent(1617906060, 4500.00, "user2", "serviceB"));
//        outOfOrderEvents.add(new TransactionEvent(1617906120, 75.00, "user1", "serviceC"));
//        outOfOrderEvents.add(new TransactionEvent(1617906180, 3000.00, "user3", "serviceA"));
//        outOfOrderEvents.add(new TransactionEvent(1617906240, 200.00, "user1", "serviceB"));
//        outOfOrderEvents.add(new TransactionEvent(1617906300, 4800.00, "user2", "serviceC"));
//        outOfOrderEvents.add(new TransactionEvent(1617906360, 100.00, "user4", "serviceA"));
//        outOfOrderEvents.add(new TransactionEvent(1617906420, 4900.00, "user3", "serviceB"));
//        outOfOrderEvents.add(new TransactionEvent(1617906480, 120.00, "user1", "serviceD"));
//        outOfOrderEvents.add(new TransactionEvent(1617906540, 5000.00, "user3", "serviceC"));
        return outOfOrderEvents;

    }
}


//import io.marcus.model.Alert;
//import io.marcus.model.TransactionEvent;
//import io.marcus.service.FraudDetectionService;
//
//import java.io.IOException;
//import java.util.*;
//import java.io.*;


//public class Main {
//    public static void main(String[] args) {
//        List<TransactionEvent> outOfOrderEvents;
////        if(args.length != 1){
////            System.out.println("Usage: java Main <data_file>");
////            return;
////        }
//        String fileName = null; // args[0]
//
//        if (fileName != null) {
//            // Read events from the specified file path
//            outOfOrderEvents = readEventsFromFile(fileName);
//        } else {
//            outOfOrderEvents = createTestOutOfOrderDataset();
//        }

        // Simulate a test dataset of transaction events
//        List<TransactionEvent> transactionEvents = createTestDataset();
        // Simulate a test dataset of transaction events (potentially out-of-order)

        // Set the window size based on the time window constraints
//        long windowSizeInSeconds = 300; // 5 minutes

        // Initialize a sliding window to hold events within the specified time window
//        Deque<TransactionEvent> slidingWindow = new ArrayDeque<>();

//        for (TransactionEvent event : outOfOrderEvents) {
        // Remove outdated events from the sliding window
//            while (!slidingWindow.isEmpty() && event.getTimestamp() - slidingWindow.getFirst().getTimestamp() > windowSizeInSeconds) {
//                slidingWindow.removeFirst();
//            }

        // Add the current event to the sliding window
//            slidingWindow.addLast(event);
//            }


        // Detect fraud and get alerts based on events within the sliding window
//        List<Alert> alerts = FraudDetectionService.detectFraud(slidingWindow);
        // Print or log alerts
//        for (Alert alert : alerts) {
//            System.out.println("Alert for user " + alert.getUserID() + ": " + alert.getAlertMessage());

//        }
//    }

//        private static List<TransactionEvent> createTestOutOfOrderDataset () {
            // Simulate a test dataset of out-of-order transaction events
//            List<TransactionEvent> outOfOrderEvents = new ArrayList<>();

//        input dataset from instructions
//            outOfOrderEvents.add(new TransactionEvent(1617906000, 150.00, "user1", "serviceA"));
//            outOfOrderEvents.add(new TransactionEvent(1617906060, 4500.00, "user2", "serviceB"));
//            outOfOrderEvents.add(new TransactionEvent(1617906120, 75.00, "user1", "serviceC"));
//            outOfOrderEvents.add(new TransactionEvent(1617906180, 3000.00, "user3", "serviceA"));
//            outOfOrderEvents.add(new TransactionEvent(1617906240, 200.00, "user1", "serviceB"));
//            outOfOrderEvents.add(new TransactionEvent(1617906300, 4800.00, "user2", "serviceC"));
//            outOfOrderEvents.add(new TransactionEvent(1617906360, 100.00, "user4", "serviceA"));
//            outOfOrderEvents.add(new TransactionEvent(1617906420, 4900.00, "user3", "serviceB"));
//            outOfOrderEvents.add(new TransactionEvent(1617906480, 120.00, "user1", "serviceD"));
//            outOfOrderEvents.add(new TransactionEvent(1617906540, 5000.00, "user3", "serviceC"));


            // out-of-order transaction events
//        outOfOrderEvents.add(new TransactionEvent(1617906005, 150.00, "user1", "serviceA"));
//        outOfOrderEvents.add(new TransactionEvent(1617906060, 4500.00, "user2", "serviceB"));
//        outOfOrderEvents.add(new TransactionEvent(1617906120, 75.00, "user1", "serviceC"));
//        outOfOrderEvents.add(new TransactionEvent(1617906240, 200.00, "user1", "serviceB"));
//        outOfOrderEvents.add(new TransactionEvent(1617906180, 3000.00, "user3", "serviceA"));
//        outOfOrderEvents.add(new TransactionEvent(1617906360, 100.00, "user4", "serviceA"));
//        outOfOrderEvents.add(new TransactionEvent(1617906300, 4800.00, "user2", "serviceC"));
//        outOfOrderEvents.add(new TransactionEvent(1617906420, 4900.00, "user3", "serviceB"));
//        outOfOrderEvents.add(new TransactionEvent(1617906540, 5000.00, "user3", "serviceC"));
//        outOfOrderEvents.add(new TransactionEvent(1617906480, 120.00, "user1", "serviceD"));

//            return outOfOrderEvents;
//        }
//
//        private static List<TransactionEvent> readEventsFromFile (String filePath){
//            List<TransactionEvent> events = new ArrayList<>();
//
//            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    // Parse each line as a TransactionEvent (assuming a specific format)
//                    String[] parts = line.split(",");
//                    long timestamp = Long.parseLong(parts[0]);
//                    double amount = Double.parseDouble(parts[1]);
//                    String userID = parts[2];
//                    String serviceID = parts[3];
//
//                    TransactionEvent event = new TransactionEvent(timestamp, amount, userID, serviceID);
//                    events.add(event);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return events;
//        }
//
//    }}
