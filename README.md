# Fight-Radar Simulation

## Authors
Mateo Martínez Sanz  
Hugo Mata Merino  

---

## Project Description

This project simulates the behavior of the Fight-Radar application.

The application launches a weekly yes/no question about a trending topic.  
Users are assigned to one of two groups depending on their answer.

Each user has:
- An ID
- X and Y coordinates (2D space)
- A group
- A list of neighbors

A user considers another user as a neighbor if they are within a **2-meter radius**.  
If user X is neighbor of Y, then Y is also neighbor of X.

The program analyzes how neighbor relationships change when users move.

---

## Main Features

- Load initial users from a file
- Update user positions
- Detect gained or lost neighbors
- Generate notification messages
- Measure execution time
- Perform performance analysis (growth study)

---

## Implemented Modes

### Debug Mode
- Loads an initial state file
- Loads a movement file
- Updates user positions
- Prints generated messages
- Measures execution time using `System.nanoTime()`

Used to verify correctness of the algorithm.

---

### Measurement Mode
- Loads initial users
- Asks for number of cycles
- Generates random movements (-0.5 to 0.5)
- Executes the algorithm without printing messages
- Computes average execution time

Used to analyze time complexity and performance.

---

## Complexity Analysis

Based on experimental data:

- For fixed density and variable number of users → growth is **quadratic**
- Time complexity: **O(n²)**
- For fixed n and variable density → growth is **linear**

---

## Requirements

- Java 17 or higher
- Command line access

---

## Classes
- Usuario.java
- Practica.java
