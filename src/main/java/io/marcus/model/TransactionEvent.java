package io.marcus.model;


public class TransactionEvent {
    private long timestamp;
    private double amount;
    private String userID;
    private String serviceID;

    public TransactionEvent(long timestamp, double amount, String userID, String serviceID) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.userID = userID;
        this.serviceID = serviceID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "timestamp=" + timestamp +
                ", amount=" + amount +
                ", userID='" + userID + '\'' +
                ", serviceID='" + serviceID + '\'' +
                '}';
    }
}
