# IP Geolocation Broker

A Java-based service that efficiently routes IP geolocation requests to the most reliable provider based on their performance metrics.

## Features

- Dynamic routing based on provider performance
- Thread-safe implementation for concurrent requests
- Rate limiting per provider
- Performance tracking with sliding windows
- Fallback to alternative providers on failure
- Simulated providers for testing

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── ipbroker/
│               ├── Main.java
│               ├── broker/
│               │   └── GeoLocationBroker.java
│               ├── model/
│               │   ├── Location.java
│               │   └── ProviderMetrics.java
│               └── provider/
│                   ├── GeoLocationProvider.java
│                   └── SimulatedGeoLocationProvider.java
└── test/
    └── java/
        └── com/
            └── ipbroker/
                └── [test files]
```

## Building the Project

The project uses Maven for build management. To build the project:

```bash
mvn clean package
```

This will create an executable JAR file in the `target` directory.

## Running the Application

After building, you can run the application using:

```bash
java -jar target/ip-geolocation-broker-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Implementation Details

The broker system implements the following key components:

1. **Location Model**: Represents geolocation data with country and city
2. **Provider Metrics**: Tracks performance metrics for each provider
3. **Provider Interface**: Defines the contract for all providers
4. **Simulated Provider**: Simulates a real provider with configurable behavior
5. **Broker**: Manages multiple providers and implements smart routing

The system uses a scoring mechanism to determine the best provider based on:
- Error count (heavily weighted)
- Average response time
- Current request count 