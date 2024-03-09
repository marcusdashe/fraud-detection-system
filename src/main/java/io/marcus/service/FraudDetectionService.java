package io.marcus.service;


//package io.marcus.service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//import io.marcus.model.Alert;
//import io.marcus.model.TransactionEvent;
//import io.marcus.util.TimeUtils;
//
//public class FraudDetectionService {
//
//    private static final int DISTINCT_SERVICES_THRESHOLD = 3;
//    private static final double TRANSACTION_AMOUNT_THRESHOLD = 5.0;
//    private static final long FIVE_MINUTES_IN_SECONDS = 300;
//    private static final long TEN_MINUTES_IN_SECONDS = 600;
//
//    public static List<Alert> detectFraud(Deque<TransactionEvent> slidingWindow) {
//        List<Alert> alerts = new ArrayList<>();
//        Map<String, List<Double>> userTransactionsMap = new HashMap<>();
//
//        for (TransactionEvent event : slidingWindow) {
//            String userID = event.getUserID();
//            long currentTimestamp = TimeUtils.getCurrentTimestampInSeconds();
//
//            if (isDistinctServicesWithinTimeWindow(slidingWindow, userID, currentTimestamp)) {
//                alerts.add(new Alert(userID, "User conducted transactions in more than 3 distinct services within a 5-minute window"));
//            }
//
//            if (isHighTransactionAmount(slidingWindow, userTransactionsMap, userID, event.getAmount())) {
//                alerts.add(new Alert(userID, "User has transactions significantly higher than typical amounts"));
//            }
//
//            if (isPingPongActivity(slidingWindow, userID, event.getServiceID(), currentTimestamp)) {
//                alerts.add(new Alert(userID, "User involved in ping-pong activity within 10 minutes"));
//            }
//
//            updateUserTransactionsMap(userTransactionsMap, userID, event.getAmount());
//        }
//
//        return alerts;
//    }
//
//    private static boolean isDistinctServicesWithinTimeWindow(Deque<TransactionEvent> slidingWindow, String userID, long currentTimestamp) {
//        int distinctServicesCount = 0;
//
//        for (TransactionEvent event : slidingWindow) {
//            if (event.getUserID().equals(userID) &&
//                    TimeUtils.areEventsWithinTimeWindow(event.getTimestamp(), currentTimestamp, FIVE_MINUTES_IN_SECONDS)) {
//                distinctServicesCount++;
//                if (distinctServicesCount >= DISTINCT_SERVICES_THRESHOLD) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    private static boolean isHighTransactionAmount(Deque<TransactionEvent> slidingWindow, Map<String, List<Double>> userTransactionsMap, String userID, double currentAmount) {
//        List<Double> userTransactions = slidingWindow.stream()
//                .filter(event -> event.getUserID().equals(userID))
//                .map(TransactionEvent::getAmount)
//                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
//
//        double averageTransactionAmount = userTransactions.isEmpty() ? 0.0 :
//                userTransactions.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
//
//        return currentAmount > TRANSACTION_AMOUNT_THRESHOLD * averageTransactionAmount;
//    }
//
//    private static boolean isPingPongActivity(Deque<TransactionEvent> slidingWindow, String userID, String currentServiceID, long currentTimestamp) {
//        long pingPongWindowEndTimestamp = currentTimestamp - TEN_MINUTES_IN_SECONDS;
//        boolean pingPongStarted = false;
//        String previousServiceID = null;
//
//        for (TransactionEvent event : slidingWindow) {
//            if (event.getUserID().equals(userID) && TimeUtils.areEventsWithinTimeWindow(event.getTimestamp(), currentTimestamp, TEN_MINUTES_IN_SECONDS)) {
//                if (pingPongStarted && event.getServiceID().equals(previousServiceID) && !event.getServiceID().equals(currentServiceID)) {
//                    return true;
//                }
//
//                pingPongStarted = true;
//                previousServiceID = event.getServiceID();
//            }
//
//            if (event.getTimestamp() < pingPongWindowEndTimestamp) {
//                break;
//            }
//        }
//
//        return false;
//    }
//
//    private static void updateUserTransactionsMap(Map<String, List<Double>> userTransactionsMap, String userID, double currentAmount) {
//        userTransactionsMap.computeIfAbsent(userID, k -> new ArrayList<>()).add(currentAmount);
//    }


//    public static List<Alert> detectFraud(Deque<TransactionEvent> slidingWindow) {
//        List<Alert> alerts = new ArrayList<>();
//
//        // Map to store user-wise transactions for calculating averages
//        Map<String, List<Double>> userTransactionsMap = new HashMap<>();
//
//        for (TransactionEvent event : slidingWindow) {
//            String userID = event.getUserID();
//            long currentTimestamp = TimeUtils.getCurrentTimestampInSeconds();
//
//            // Check for distinct services within a 5-minute window
//            if (isDistinctServicesWithinTimeWindow(slidingWindow, userID, event.getTimestamp())) {
//                alerts.add(new Alert(userID, "User conducted transactions in more than 3 distinct services within a 5-minute window"));
//            }
//
//            // Check for transactions 5x above the average in the last 24 hours
//            if (isHighTransactionAmount(slidingWindow, userTransactionsMap, userID, event.getAmount())) {
//                alerts.add(new Alert(userID, "User has transactions significantly higher than typical amounts"));
//            }
//
//            // Check for ping-pong activity within 10 minutes
//            if (isPingPongActivity(slidingWindow, userID, event.getServiceID(), currentTimestamp)) {
//                alerts.add(new Alert(userID, "User involved in ping-pong activity within 10 minutes"));
//            }
//
//            // Update user-wise transaction map
//            userTransactionsMap.computeIfAbsent(userID, k -> new ArrayList<>()).add(event.getAmount());
//        }
//
//        return alerts;
//    }
//
//
//
//    private static boolean isDistinctServicesWithinTimeWindow(Deque<TransactionEvent> slidingWindowEvents, String userID, long currentTimestamp) {
//        int distinctServicesCount = 0;
//
//        for (TransactionEvent event : slidingWindowEvents) {
//            if (event.getUserID().equals(userID) &&
//                    TimeUtils.areEventsWithinTimeWindow(event.getTimestamp(), currentTimestamp, TimeUtils.FIVE_MINUTES_IN_SECONDS)) {
//                distinctServicesCount++;
//                if (distinctServicesCount >= DISTINCT_SERVICES_THRESHOLD) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//    private static boolean isHighTransactionAmount(Deque<TransactionEvent> slidingWindowEvents,
//                                                   Map<String, List<Double>> userTransactionsMap,
//                                                   String userID,
//                                                   double currentAmount
//    ) {
//
//        // Get user transactions from the sliding window (assuming timestamps are stored)
//        List<Double> userTransactions = slidingWindowEvents.stream()
//                .filter(event -> event.getUserID().equals(userID))
//                .mapToDouble(TransactionEvent::getAmount)
//                .boxed() // Convert to Double for average calculation
//                .collect(Collectors.toList());
//
//        // Calculate average of transactions within the sliding window
//        double averageTransactionAmount = userTransactions.isEmpty() ? 0.0 : userTransactions.stream()
//                .mapToDouble(Double::doubleValue)
//                .average()
//                .orElse(0.0);
//
//        return currentAmount > TRANSACTION_AMOUNT_THRESHOLD * averageTransactionAmount;
//    }
//
//
//    private static boolean isPingPongActivity(Deque<TransactionEvent> slidingWindowEvents, String userID, String currentServiceID, long currentTimestamp) {
//        long pingPongWindowEndTimestamp = currentTimestamp - TimeUtils.TEN_MINUTES_IN_SECONDS;
//
//        boolean pingPongStarted = false;
//        String previousServiceID = null;
//
//        for (TransactionEvent event : slidingWindowEvents) {
//            if (event.getUserID().equals(userID) && TimeUtils.areEventsWithinTimeWindow(event.getTimestamp(), currentTimestamp, TimeUtils.TEN_MINUTES_IN_SECONDS)) {
//                if (pingPongStarted) {
//                    if (event.getServiceID().equals(previousServiceID) && !event.getServiceID().equals(currentServiceID)) {
//                        return true;
//                    }
//                }
//
//                // Update for potential ping-pong start
//                pingPongStarted = true;
//                previousServiceID = event.getServiceID();
//            }
//
//            // If the event is older than the ping-pong window, stop checking
//            if (event.getTimestamp() < pingPongWindowEndTimestamp) {
//                break;
//            }
//        }
//
//        return false;
//    }
//
//    private static void updateUserTransactionsMap(Map<String, List<Double>> userTransactionsMap, String userID, double currentAmount) {
//        userTransactionsMap.computeIfAbsent(userID, k -> new ArrayList<>()).add(currentAmount);
//    }
//}



//package io.marcus.service;
//
//import io.marcus.model.Alert;
//import io.marcus.model.TransactionEvent;
//import io.marcus.util.TimeUtils;
//
//import java.util.*;
//
//public class FraudDetectionService {
//
//    private static final int DISTINCT_SERVICES_THRESHOLD = 3;
//    private static final double TRANSACTION_AMOUNT_THRESHOLD = 5.0;
//    private static final long FIVE_MINUTES_IN_SECONDS = 300;
//    private static final long TEN_MINUTES_IN_SECONDS = 600;
//
//    public static List<Alert> detectFraud(Deque<TransactionEvent> slidingWindow) {
//        List<Alert> alerts = new ArrayList<>();
//        Map<String, List<Double>> userTransactionsMap = new HashMap<>();
//        Map<String, Set<String>> userDistinctServicesMap = new HashMap<>();
//
//        for (TransactionEvent event : slidingWindow) {
//            String userID = event.getUserID();
//            long currentTimestamp = TimeUtils.getCurrentTimestampInSeconds();
//
//            // Check for distinct services within a 5-minute window
//            Set<String> distinctServices = userDistinctServicesMap.computeIfAbsent(userID, k -> new HashSet<>());
//            distinctServices.add(event.getServiceID());
//
//            if (distinctServices.size() >= DISTINCT_SERVICES_THRESHOLD &&
//                    TimeUtils.areEventsWithinTimeWindow(event.getTimestamp(), currentTimestamp, FIVE_MINUTES_IN_SECONDS)) {
//                alerts.add(new Alert(userID, "User conducted transactions in more than 3 distinct services within a 5-minute window"));
//            }
//
//            // Check for transactions 5x above the average in the last 24 hours
//            double currentAmount = event.getAmount();
//            List<Double> userTransactions = userTransactionsMap.computeIfAbsent(userID, k -> new ArrayList<>());
//            userTransactions.add(currentAmount);
//
//            double averageTransactionAmount = calculateAverage(userTransactions);
//            if (currentAmount > TRANSACTION_AMOUNT_THRESHOLD * averageTransactionAmount) {
//                alerts.add(new Alert(userID, "User has transactions significantly higher than typical amounts"));
//            }
//
//            // Check for ping-pong activity within 10 minutes
//            if (isPingPongActivity(slidingWindow, userID, event.getServiceID(), currentTimestamp)) {
//                alerts.add(new Alert(userID, "User involved in ping-pong activity within 10 minutes"));
//            }
//        }
//
//        return alerts;
//    }
//
//    private static double calculateAverage(List<Double> values) {
//        return values.isEmpty() ? 0.0 : values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
//    }
//
//    private static boolean isPingPongActivity(Deque<TransactionEvent> slidingWindow, String userID, String currentServiceID, long currentTimestamp) {
//        long pingPongWindowEndTimestamp = currentTimestamp - TEN_MINUTES_IN_SECONDS;
//        boolean pingPongStarted = false;
//        String previousServiceID = null;
//
//        for (TransactionEvent event : slidingWindow) {
//            if (event.getUserID().equals(userID) && TimeUtils.areEventsWithinTimeWindow(event.getTimestamp(), currentTimestamp, TEN_MINUTES_IN_SECONDS)) {
//                if (pingPongStarted && event.getServiceID().equals(previousServiceID) && !event.getServiceID().equals(currentServiceID)) {
//                    return true;
//                }
//
//                pingPongStarted = true;
//                previousServiceID = event.getServiceID();
//            }
//
//            if (event.getTimestamp() < pingPongWindowEndTimestamp) {
//                break;
//            }
//        }
//
//        return false;
//    }
//}


//import io.marcus.model.Alert;
//import io.marcus.model.TransactionEvent;
//import io.marcus.util.TimeUtils;
//
//import java.util.*;
//
//public class FraudDetectionService {
//    private static final long FIVE_MINUTES_IN_SECONDS = TimeUtils.FIVE_MINUTES_IN_SECONDS;
//
//    // Map to store user transactions
//    private final Map<String, TreeMap<Long, Set<String>>> userTransactionMap = new HashMap<>();
//
//    // Method to process a new transaction event
//    public void processTransaction(TransactionEvent transactionEvent) {
//        String userID = transactionEvent.getUserID();
//        String serviceID = transactionEvent.getServiceID();
//        long currentTimestamp = transactionEvent.getTimestamp();
//
//        // Check if the user has previous transactions
//        if (userTransactionMap.containsKey(userID)) {
//            TreeMap<Long, Set<String>> userTransactions = userTransactionMap.get(userID);
//
//            // Remove outdated transactions from the sliding window
//            long windowStart = currentTimestamp - FIVE_MINUTES_IN_SECONDS;
//            userTransactions.headMap(windowStart, false).clear();
//
//            // Check if the transaction is within the time window
//            if (TimeUtils.areEventsWithinTimeWindow(currentTimestamp, userTransactions.lastKey(), FIVE_MINUTES_IN_SECONDS)) {
//                // Add the service to the set for the current timestamp
//                userTransactions.computeIfAbsent(currentTimestamp, k -> new HashSet<>()).add(serviceID);
//
//                // Check if the user has transactions in more than 3 distinct services
//                if (distinctServiceCount(userTransactions) > 3) {
//                    raiseAlert(userID);
//                }
//            } else {
//                // Create a new entry for the current timestamp and service
//                userTransactions.put(currentTimestamp, new HashSet<>(Collections.singletonList(serviceID)));
//            }
//        } else {
//            // Add the user to the map if it's their first transaction
//            TreeMap<Long, Set<String>> userTransactions = new TreeMap<>();
//            userTransactions.put(currentTimestamp, new HashSet<>(Collections.singletonList(serviceID)));
//            userTransactionMap.put(userID, userTransactions);
//        }
//    }
//
//    // Method to count the distinct services in the sliding window
//    private int distinctServiceCount(TreeMap<Long, Set<String>> userTransactions) {
//        Set<String> distinctServices = new HashSet<>();
//        userTransactions.forEach((timestamp, services) -> distinctServices.addAll(services));
//        return distinctServices.size();
//    }
//
//    // Method to raise an alert for a suspicious user
//    private void raiseAlert(String userID) {
//        String alertMessage = "User conducted transactions in more than 3 distinct services within a 5-minute window.";
//        Alert alert = new Alert(userID, alertMessage);
//        System.out.println(alert.toString());
//        // Additional logic to handle the alert (e.g., store in a database, send notification, etc.)
//    }
//
//
//}

//import io.marcus.model.TransactionEvent;
//import io.marcus.util.TimeUtils;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class FraudDetectionService {
//
//    private final long FIVE_MINUTES_IN_SECONDS = TimeUtils.FIVE_MINUTES_IN_SECONDS;
//
//    // Data structure to track user activity within the 5-minute window
//    private Map<String,Deque<TransactionEvent>> userActivityInWindow = new ConcurrentHashMap<>();
//
//    public boolean isUserInMultipleServicesWithinWindow(TransactionEvent event) {
//        String userId = event.getUserID();
//        long timestamp = event.getTimestamp();
//        String serviceId = event.getServiceID();
//
//        // Get the user's existing transaction history in the window (if any)
//        Deque<TransactionEvent> userEvents = userActivityInWindow.get(userId);
//
//        // If no history exists, create a new deque and add the current event
//        if (userEvents == null) {
//            userEvents = new LinkedList<>();
//            userActivityInWindow.put(userId, userEvents);
//        } else {
//            // Remove events older than the 5-minute window
//            removeOutdatedEvents(userEvents, timestamp);
//        }
//
//        // Add the current event to the user's history
//        userEvents.addLast(event);
//
//        // Check if the user used more than 3 distinct services within the window
//        Set<String> usedServices = new HashSet<>();
//        for (TransactionEvent e : userEvents) {
//            usedServices.add(e.getServiceID());
//            if (usedServices.size() > 3) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private void removeOutdatedEvents(Deque<TransactionEvent> userEvents, long currentTimestamp) {
//        while (!userEvents.isEmpty() &&
//                currentTimestamp - userEvents.getFirst().getTimestamp() > FIVE_MINUTES_IN_SECONDS) {
//            userEvents.removeFirst();
//        }
//    }
//}



import io.marcus.model.Alert;
import io.marcus.model.TransactionEvent;
import io.marcus.util.TimeUtils;

import java.util.*;

public class FraudDetectionService {

    // Map to store the services used by each user within the time window
    private final Map<String, Map<String, Long>> userServicesMap = new HashMap<>();

    // Map to store the transaction amounts for each user in the last 24 hours
    private final Map<String, List<Double>> userTransactionAmounts = new HashMap<>();

    // Map to store the transaction timestamps for each user
    private final Map<String, List<Long>> userTransactionTimestamps = new HashMap<>();


    // Method to process a transaction event
    public void processTransactionEvent(TransactionEvent event) {
        handleOutOfOrderEvents(event);

        // Check if the user has conducted transactions in more than 3 distinct services within a 5-minute window
        if (isSuspiciousActivity(event)) {
            generateAlert(event.getUserID(), "Suspicious activity: More than 3 distinct services in 5 minutes");
        }

        // Check for suspicious activity based on transaction amounts
        if (isSuspiciousActivityTransactionAmount(event)) {
            generateAlert(event.getUserID(), "Suspicious activity: Transaction amount 5x above average in the last 24 hours");
        }
    }

    // Method to handle out-of-order events
    private void handleOutOfOrderEvents(TransactionEvent event) {
        long currentTimestamp = TimeUtils.getCurrentTimestampInSeconds();

        // Adjust the timestamp if the event is out of order
        if (event.getTimestamp() > currentTimestamp) {
            System.out.println("Out-of-order event detected: " + event);
            // You can log or handle out-of-order events based on your specific requirements.
            // For example, you might decide to reject the event or log it for further analysis.
        }
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
        // Check if the user has used more than 3 distinct services within the 5-minute window
        Set<String> distinctServices = userServices.keySet();

        return distinctServices.size() > 3;
    }

    // Method to check for suspicious activity based on transaction amounts
    private boolean isSuspiciousActivityTransactionAmount(TransactionEvent event) {
        String userID = event.getUserID();
        double amount = event.getAmount();
        long timestamp = event.getTimestamp();

        userTransactionTimestamps.putIfAbsent(userID, new ArrayList<>());
        userTransactionAmounts.putIfAbsent(userID, new ArrayList<>());

        List<Long> transactionTimestamps = userTransactionTimestamps.get(userID);
        List<Double> transactionAmounts = userTransactionAmounts.get(userID);

        // Add the current transaction timestamp and amount
        transactionTimestamps.add(timestamp);
        transactionAmounts.add(amount);

        // Remove transaction timestamps and amounts that are older than 24 hours
        long twentyFourHoursAgo = timestamp - TimeUtils.TWENTY_FOUR_HOURS_IN_SECONDS;

        transactionTimestamps.removeIf(ts -> ts <= twentyFourHoursAgo);
        transactionAmounts.removeIf(amt -> transactionTimestamps.get(transactionAmounts.indexOf(amt)) <= twentyFourHoursAgo);

        // Calculate the average transaction amount in the last 24 hours
        double averageAmount = calculateAverage(transactionAmounts);

        // Check if the current transaction amount is 5 times above the average
        return amount > 5 * averageAmount;
    }

    // Method to calculate the average of a list of doubles
    private double calculateAverage(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }


    // Method to calculate the average of a list of doubles
//    private double calculateAverage(List<Double> values) {
//        if (values.isEmpty()) {
//            return 0.0;
//        }
//        double sum = 0.0;
//        for (double value : values) {
//            sum += value;
//        }
//        return sum / values.size();
//    }


    // Method to generate an alert
    private void generateAlert(String userID, String message) {
        Alert alert = new Alert(userID, message);
        System.out.println("Fraudulent Alert: " + alert);
        // You can handle the alert here, such as sending notifications or logging.
    }
}

