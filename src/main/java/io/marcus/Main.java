package io.marcus;


import io.marcus.model.TransactionEvent;
import io.marcus.service.FraudDetectionService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    // Map to store the transaction amounts for each user in the last 24 hours
    private static final Map<String, List<Double>> userTransactionAmounts = new HashMap<>();

    // Map to store the transaction timestamps for each user
    private static final Map<String, List<Long>> userTransactionTimestamps = new HashMap<>();

    // Map to store the services sequence for each user within the time window
    private static final Map<String, List<String>> userServicesSequenceMap = new HashMap<>();

    public static void main(String[] args) {
        /**
         *
         * Uncomment if dataset will be read from CSV file
         *
         * ****/
        if(args.length != 1){
            System.out.println(args[0]);
          System.out.println("Usage: java Main <data_file>");
          return;
       }

        List<TransactionEvent> transactions;
        String fileName =  args[0];

        if (fileName != null) {
            // Read events from the specified file path
           transactions = readEventsFromFile(fileName);
        } else {
             transactions = generateDataset();
        }

        // Get the test dataset
//        List<TransactionEvent> transactions = generateDataset();

        for(TransactionEvent e: transactions){
            updateUserTransactions(e);
        }

//        System.out.println(userTransactionAmounts);
//        System.out.println(userTransactionTimestamps);
//        System.out.println(userServicesSequenceMap);

        // Create a  FraudDetectionService instance
        FraudDetectionService fraudDetection = new FraudDetectionService(
                userTransactionAmounts,
                userTransactionTimestamps,
                userServicesSequenceMap
        );

        for (TransactionEvent event : transactions) {
             fraudDetection.processTransactionEvent(event);
        }
    }

    static void updateUserTransactions(TransactionEvent event) {
        String userId = event.getUserID();
        double amount = event.getAmount();
        long timestamp = event.getTimestamp();
        String serviceId = event.getServiceID();

        // Update userTransactionAmounts
        userTransactionAmounts.computeIfAbsent(userId, k -> new ArrayList<>()).add(amount);

        // Update userTransactionTimestamps
        userTransactionTimestamps.computeIfAbsent(userId, k -> new ArrayList<>()).add(timestamp);

        // Update userServicesSequenceMap
        userServicesSequenceMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(serviceId);
    }

    // Method to create the test dataset
    private static List<TransactionEvent> generateDataset() {

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


        return outOfOrderEvents;

    }

    private static List<TransactionEvent> readEventsFromFile (String filePath){
            List<TransactionEvent> events = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Parse each line as a TransactionEvent (assuming a specific format - CSV)
                    String[] parts = line.split(",");
                    long timestamp = Long.parseLong(parts[0]);
                    double amount = Double.parseDouble(parts[1]);
                    String userID = parts[2];
                    String serviceID = parts[3];

                    TransactionEvent event = new TransactionEvent(timestamp, amount, userID, serviceID);
                    events.add(event);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return events;
        }

}
