package io.marcus.model;


public class Alert {
    private String userID;
    private String alertMessage;

    public Alert(String userID, String alertMessage) {
        this.userID = userID;
        this.alertMessage = alertMessage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "userID='" + userID + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                '}';
    }
}

