package io.marcus.service;


import io.marcus.model.Alert;
import io.marcus.model.TransactionEvent;
import io.marcus.util.TimeUtils;

import java.util.*;

public class FraudDetectionService {

    // Map to store the transaction amounts for each user in the last 24 hours
    private final Map<String, List<Double>> userTransactionAmounts;

    // Map to store the transaction timestamps for each user
    private final Map<String, List<Long>> userTransactionTimestamps;

    // Map to store the services used by each user within the time window
    private final Map<String, Map<String, Long>> userServicesMap = new HashMap<>();

    // Map to store the services sequence for each user within the time window
    private final Map<String, List<String>> userServicesSequenceMap;



    public FraudDetectionService(
            Map<String, List<Double>> userTransactionAmounts,
            Map<String, List<Long>> userTransactionTimestamps,
            Map<String, List<String>> userServicesSequenceMap

    ) {
        this.userTransactionAmounts = userTransactionAmounts;
        this.userTransactionTimestamps = userTransactionTimestamps;
        this.userServicesSequenceMap = userServicesSequenceMap;
    }


    // Method to process a transaction event
    public void processTransactionEvent(TransactionEvent event) {

        handleOutOfOrderEvents(event);

        if (isSuspiciousActivity(event)) {
            generateAlert(event.getUserID(), "Suspicious activity: More than 3 distinct services in 5 minutes");
        }

        if (isHighTransactionAmount(event)) {
            generateAlert(event.getUserID(), "Suspicious activity: Transaction amount 5x above the average in the last 24 hours");
        }

        if (isPingPongActivity(event)) {
            generateAlert(event.getUserID(), "Suspicious activity: Ping-pong activity detected within 10 minutes");
        }
    }

    // Method to handle out-of-order events
    private void handleOutOfOrderEvents(TransactionEvent event) {
        long currentTimestamp = TimeUtils.getCurrentTimestampInSeconds();

        // Log event if the event is out of order
        if (event.getTimestamp() > currentTimestamp) {
            System.out.println("Out-of-order event detected: " + event);
        }
        // Update the timestamp to the current timestamp
        event.setTimestamp(currentTimestamp);
    }


    // Method to check for suspicious activity based on distinct services
    private boolean isSuspiciousActivity(TransactionEvent event) {
        String userID = event.getUserID();
        String serviceID = event.getServiceID();
        long timestamp = event.getTimestamp();

        userServicesMap.putIfAbsent(userID, new HashMap<>());
        Map<String, Long> userServices = userServicesMap.get(userID);

        // Remove services that are older than 5 minutes
        userServices.entrySet().removeIf(entry -> !TimeUtils.areEventsWithinTimeWindow(entry.getValue(), timestamp, TimeUtils.FIVE_MINUTES_IN_SECONDS));

        // Update the timestamp of the current service
        userServices.put(serviceID, timestamp);

        // Check if the user has used more than 3 distinct services within the 5-minute window
        Set<String> distinctServices = userServices.keySet();

        return distinctServices.size() > 3;
    }

    // Method to check for suspicious activity based on transaction amount
    private boolean isHighTransactionAmount(TransactionEvent event) {
        String userID = event.getUserID();
        double amount = event.getAmount();
        long timestamp = event.getTimestamp();

        // Get the user's transaction amounts in the last 24 hours
        List<Double> userAmounts = userTransactionAmounts.getOrDefault(userID, new ArrayList<>());

        // Remove transactions older than 24 hours
        cleanOldTransactions(userAmounts, timestamp, TimeUtils.TWENTY_FOUR_HOURS_IN_SECONDS);

        // Calculate the user's average amount in the last 24 hours
        double averageAmount = calculateUserAverage(userAmounts);

        // Check if the transaction amount is 5x above the average
        boolean suspiciousAmount = amount > (5 * averageAmount);


//        System.out.println("UserID: " + userID + ", Transaction Amount: " + amount + ", Average Amount: " + averageAmount);

        return suspiciousAmount;
    }

    // Method to check for suspicious activity based on "ping-pong" activity
    private boolean isPingPongActivity(TransactionEvent event) {
        String userID = event.getUserID();
        String serviceID = event.getServiceID();
        long timestamp = event.getTimestamp();

        userServicesMap.putIfAbsent(userID, new HashMap<>());
        userServicesSequenceMap.putIfAbsent(userID, new ArrayList<>());

        Map<String, Long> userServices = userServicesMap.get(userID);
        List<String> userServicesSequence = userServicesSequenceMap.get(userID);

        // Remove services that are older than 10 minutes
        userServices.entrySet().removeIf(entry -> !TimeUtils.areEventsWithinTimeWindow(entry.getValue(), timestamp, TimeUtils.TEN_MINUTES_IN_SECONDS));

        // Update the timestamp of the current service
        userServices.put(serviceID, timestamp);

        // Update the service sequence
        userServicesSequence.add(serviceID);

        // Check if there is a ping-pong activity (bouncing back and forth between two services)
        return hasPingPongActivity(userServicesSequence, serviceID);
    }

    private boolean hasPingPongActivity(List<String> userServicesSequence, String currentService) {
        int sequenceSize = userServicesSequence.size();

        if (sequenceSize >= 4) {
            // Check the last three services in the sequence
            String lastService = userServicesSequence.get(sequenceSize - 1);
            String secondLastService = userServicesSequence.get(sequenceSize - 2);
            String thirdLastService = userServicesSequence.get(sequenceSize - 3);

            // Check if there is a ping-pong activity
            return currentService.equals(secondLastService) && lastService.equals(thirdLastService);
        }

        return false;
    }

    // Helper method to clean old transactions from user amounts and timestamps
    private void cleanOldTransactions(List<Double> amounts, long currentTimestamp, long windowInSeconds) {
        Iterator<Double> amountIterator = amounts.iterator();
        Iterator<Long> timestampIterator = userTransactionTimestamps.getOrDefault(amounts, new ArrayList<>()).iterator();

        while (amountIterator.hasNext() && timestampIterator.hasNext()) {
            double amount = amountIterator.next();
            long timestamp = timestampIterator.next();

            if (!TimeUtils.areEventsWithinTimeWindow(timestamp, currentTimestamp, windowInSeconds)) {
                amountIterator.remove();
                timestampIterator.remove();
            }
        }
    }

    private double calculateUserAverage(List<Double> amounts) {
        if (amounts.isEmpty()) {
            return 0.0;  // Return 0 if the list is empty to avoid division by zero
        }
        double sum = 0.0;
        for (double amount : amounts) {
            sum += amount;
        }
        return sum / amounts.size();
    }

    // Method to generate an alert
    private void generateAlert(String userID, String message) {
        Alert alert = new Alert(userID, message);
        System.out.println( alert.getUserID() + " " + alert.getAlertMessage());
        // You can handle the alert here, such as sending notifications or logging.
    }
}

