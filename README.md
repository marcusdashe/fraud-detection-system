
# Real-Time Fraud Detection System

## Setup:
- Compile and run Main.java using Java.
- No external dependencies are required.

### Running the Prototype

1. **Compile and Run:**
    - Compile the Java files using `javac Main.java` 
    - Run the prototype with `java Main`

2. **Dataset Options:**
    - The program generates a test dataset by default. If you want to read from a CSV file, uncomment the relevant code in `Main.java` and provide the file path as a command-line argument.

### Algorithm Description

- The prototype implements a fraud detection algorithm for financial transactions.
- It maintains maps to store user transaction amounts, timestamps, and service sequences within a time window.
- Detection logic includes checks for the number of distinct services, high transaction amounts, and ping-pong activity.
- Alerts are generated for suspicious activities.

### Handling Out-of-Order Events

- Out-of-order events are handled in the `handleOutOfOrderEvents` method in `FraudDetectionService.java`.
- If an event timestamp is in the future, it is logged as an out-of-order event, and the timestamp is updated to the current timestamp.
- This ensures accurate processing even if events arrive in an unexpected order.

### Solution Constraints

- **Out-of-Order Event Processing:**
    - The solution addresses out-of-order event processing by detecting events with timestamps in the future.
    - The system logs such events and updates their timestamps to the current timestamp.
    - This ensures that events are processed sequentially based on their corrected timestamps, mitigating the impact of out-of-order arrivals.

The fraud detection prototype is designed to handle real-time financial transactions efficiently, providing a robust solution that adapts to the constraints imposed by potential out-of-order event occurrences.

### Expected Outcome

> $ user1 Suspicious activity: More than 3 distinct services in 5 minutes

