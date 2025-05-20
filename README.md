Please go through this docs :- https://shubhamkhaushik.atlassian.net/wiki/x/q4AB

CMD to run :- mvn clean compile exec:java -Dexec.mainClass="com.ipbroker.Main"

# IP Geolocation Broker

A Java-based service that acts as a broker for IP geolocation services, managing multiple third-party providers and implementing smart routing based on performance metrics.

## Features

- **Multiple Service Provider Support**: Integrates with multiple IP geolocation service providers
- **Smart Provider Selection**: Automatically selects the best provider based on:
  - Response time
  - Error rates
  - API limits
- **Performance Monitoring**: Tracks metrics for each provider including:
  - Response times
  - Error counts
  - Success rates
- **Automatic Maintenance**:
  - API limit resets every minute
  - Metric filtering every 10 minutes
  - Thread-safe operations using ConcurrentHashMap

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Internet connection for API calls

## Project Structure

```
src/main/java/com/ipbroker/
├── Main.java                    # Application entry point
├── controller/
│   └── GeoLocationFinder.java   # Main broker logic
├── model/
│   ├── Location.java           # Location data model
│   ├── ProviderMetricNode.java # Linked list node for metrics
│   ├── Response.java           # API response model
│   ├── ThirdPartyService.java  # Service provider model
│   └── Threshold.java          # API limit thresholds
├── service/
│   ├── FilterProviderLinkedListBasedOnTime.java  # Metric filtering
│   ├── FindBestServiceProvider.java             # Provider selection
│   ├── ResetProviderApiLimit.java               # API limit management
│   └── ThreadManager.java                       # Background task management
└── enums/
    └── ProviderMetricsStatusEnum.java           # Status enums
```

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd ip-geolocation-broker
```

2. Build the project:
```bash
mvn clean install
```

## Running the Application

To run the application:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.ipbroker.Main"
```

The application will:
1. Initialize the service providers
2. Start background threads for maintenance
3. Begin processing IP geolocation requests

## How It Works

1. **Provider Management**:
   - Each provider is tracked with its own metrics
   - API limits are automatically reset every minute
   - Performance metrics are filtered every 10 minutes

2. **Request Flow**:
   - Client sends IP address
   - Broker selects best provider based on metrics
   - Request is routed to selected provider
   - Response is tracked and metrics are updated

3. **Background Tasks**:
   - API Limit Reset: Runs every minute
   - Metric Filtering: Runs every 10 minutes
   - Thread-safe operations using ConcurrentHashMap

## Example Usage

```java
GeoLocationFinder geoLocationFinder = new GeoLocationFinder();
String result = geoLocationFinder.getGeoLocation("127.0.0.1");
System.out.println(result);
```

## Thread Safety

The application uses several mechanisms to ensure thread safety:
- ConcurrentHashMap for shared data structures
- Volatile variables for thread communication
- ScheduledExecutorService for background tasks
- Proper synchronization for metric updates

## Dependencies

- Jackson (for JSON processing)
- Java HTTP Client (for API calls)
- Maven (for build management)

## Error Handling

The system includes comprehensive error handling:
- API call failures
- Rate limiting
- Network issues
- Invalid responses

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 