#!/bin/bash

# Loop to simulate real-time data processing
while true
do
    # Append new event to the test dataset
    echo "1617909000,200.00,user4,serviceB" >> test_dataset.csv

    # Sleep for a certain interval (e.g., 5 seconds)
    sleep 5
done
