

# Reactive Programming

## What is Reactive Programming?

Reactive programming is a declarative programming paradigm that focuses on responding to changes in data and events in real-time. At its core, it revolves around three key concepts:

- **Data Streams**: A continuous flow of data objects—ranging from zero to infinite—that travel from one point to another.
- **Functional Programming**: Declarative functions used to process, transform, and manipulate the data within these streams.
- **Asynchronous Observers**: Non-blocking mechanisms that allow code to execute efficiently without waiting for events to complete.

This paradigm is designed to handle dynamic and evolving systems by enabling asynchronous and non-blocking data processing. It excels in scenarios where the system needs to react instantly to events such as user inputs, live data feeds, or messages from other systems.

---

### Visual Representation of Data Streams

Here is a visual representation of how data might flow. The data stream contains numbers but could include strings, floats, or custom objects. Note how the data doesn't arrive at regular intervals—it can be served at any time and may contain zero, one, or even infinite amounts of data.

**Example of Transformation:**
- A `map` function is applied to multiply each number by 2.
- The resulting data is filtered to include only numbers greater than 5.

**Best Practice:** Use simple, separate functions chained together rather than creating complex functions.

---

### Asynchronous vs. Synchronous Processing

Reactive programming focuses on **asynchronous processing**, where operations do not block other processes while waiting for data. This approach ensures systems can react to changes in data without halting other tasks.

---

## Publish/Subscribe Pattern

The publish/subscribe pattern is central to reactive programming. In this model:
- A **publisher** emits a data stream.
- One or more **subscribers** listen to and process the data stream in real-time.

The flow is "push-based," meaning data is sent to subscribers as soon as it's available. Subscribers can transform, filter, or otherwise manipulate the data and can also unsubscribe from updates when needed.

### Difference from Streams API

While the Streams API may seem similar, reactive programming differs in these ways:
1. Data in reactive programming is not preloaded into memory and is handled asynchronously.
2. Reactive streams utilize three distinct channels:
    - **Data Channel**: Where data arrives.
    - **Error Channel**: For handling errors.
    - **Complete Channel**: Indicates when the stream has finished.

These channels make it easier to process data, handle errors, and unsubscribe when needed.

---

## Where is it Used?

Reactive programming is widely used in scenarios requiring real-time data processing:

- **User Interactions**: Ensures responsive apps by reacting instantly to user inputs like swipes or taps.
- **Real-Time Data Feeds**: For applications like stock market updates, IoT devices, or live news feeds.
- **Messaging Systems**: Efficiently handles event-driven architectures.
- **Media Streaming**: Supports real-time chat and video streaming.

By managing asynchronous data streams, reactive programming ensures scalability, responsiveness, and resilience in modern applications.

---

## How to Use Reactive Programming

Below are examples to illustrate the use of reactive programming in practice.

### Kotlin Example

```kotlin
dataStream
    .map { it * 2 }
    .filter { it > 5 }
    .subscribe(
        onNext = { println("Data: $it") },
        onError = { println("Error: $it") },
        onComplete = { println("Stream complete") }
    )
```

Here:
- **`map`** and **`filter`** are functional programming constructs for data transformation.
- **`subscribe`** listens to three channels:
    - Data channel.
    - Error channel.
    - Complete channel.

### Imperative Code Comparison

The equivalent imperative code is verbose and harder to maintain:

```kotlin
val result = mutableListOf<Int>()
for (item in dataStream) {
    val transformed = item * 2
    if (transformed > 5) {
        result.add(transformed)
    }
}
result.forEach { println(it) }
```

Declarative programming offers a cleaner, more efficient way to handle such transformations.

---

### Combining Multiple Streams

Reactive programming allows combining multiple streams and handling errors gracefully:

```kotlin
combinedStream
    .onErrorResume { println("Error occurred, stream ended") }
    .subscribe { println(it) }
```

This ensures errors don’t crash the application and provides proper handling while completing the stream.

## Practical Use Cases and Considerations

Reactive programming shines in scenarios that require real-time data processing and responsiveness. Below are examples demonstrating where and how you might use reactive streams effectively. Each example showcases practical implementations and highlights the strengths of reactive programming.

### Real-time stock price updates

```kotlin
// Simulate a real-time stock price stream  
val stockUpdates = Observable.interval(1, TimeUnit.SECONDS)  
    .map { 
    val stockSymbol = listOf("GOOG", "AMZN", "MSFT").random() 
  val stockPrice = Random.nextInt(50, 200)  
  StockUpdate(stockSymbol, stockPrice, System.currentTimeMillis())  
  
    }  
  // Print the original data  
  .doOnNext { println("Received: $it") }  
  // Filter for stock prices greater than 100  
  .filter { stockUpdate -> stockUpdate.price > 100 }  
  // Transform data  
  .map { stockUpdate -> stockUpdate.copy(symbol = "${stockUpdate.symbol} [Processed]") }  
  
// Subscribe to consume the stock updates  
stockUpdates.subscribe(  
    { stockUpdate -> println("Processed: $stockUpdate") }, // OnNext  
  { error -> println("Stream error: ${error.message}") }, // OnError  
  { println("Stream completed") } // OnComplete  
)  
  
// Keep the main thread alive to observe the output  
Thread.sleep(20000)
```

Example of output:
```
Received: StockUpdate(symbol=GOOG, price=132, timestamp=1733742176460)
Processed: StockUpdate(symbol=GOOG [Processed], price=132, timestamp=1733742176460)
Received: StockUpdate(symbol=MSFT, price=62, timestamp=1733742177458)
Received: StockUpdate(symbol=MSFT, price=91, timestamp=1733742178456)
Received: StockUpdate(symbol=MSFT, price=165, timestamp=1733742179454)
Processed: StockUpdate(symbol=MSFT [Processed], price=165, timestamp=1733742179454)
```

This example simulates a live stream of stock price updates. Prices are filtered to include only those above 100 and transformed by adding metadata, such as marking stocks as "processed." Subscribers react to the updates in real-time, showcasing how reactive programming can handle high-frequency data streams efficiently. This approach is ideal for scenarios like stock market tracking or other real-time monitoring systems.

### Sensor Data Processing with Reactive Programming
```kotlin
 val startTime = System.currentTimeMillis()  
  
    // Simulate making asynchronous calls to 10 APIs (e.g., fetching sensor data from a remote service)  
  val sensorDataStream = (1..10).toObservable()  
        .flatMap { getSensorDataFromApiReactive(it) }  
  .doOnNext { println(it) }  
  
  sensorDataStream.blockingLast()  
  
    val endTime = System.currentTimeMillis()  
    println("Reactive execution time: ${endTime - startTime} ms")  
}  
  
fun getSensorDataFromApiReactive(id: Int): Observable<String> {  
    // Simulating an asynchronous call to an external API  
  return Observable.just("Sensor $id: Temperature ${Random.nextInt(20, 30)}°C")  
		 // Simulate network delay
        .delay(Random.nextLong(100, 200), TimeUnit.MILLISECONDS)  
```

Example output:
```
Sensor 3: Temperature 29°C
Sensor 8: Temperature 29°C
Sensor 9: Temperature 25°C
Sensor 10: Temperature 22°C
Sensor 5: Temperature 28°C
Sensor 1: Temperature 24°C
Sensor 6: Temperature 25°C
Sensor 2: Temperature 28°C
Sensor 4: Temperature 26°C
Sensor 7: Temperature 28°C
Reactive execution time: 306 ms
```
This code snippet simulates fetching sensor data from an API asynchronously for 10 sensor IDs. It creates an observable stream of integers (representing sensor IDs) and uses `flatMap` to simulate API calls that return sensor data with a random temperature. The data is processed with `doOnNext`, which prints each value as it's emitted. The `blockingLast()` operator ensures that the program waits for the completion of all asynchronous operations before calculating and printing the total execution time, giving a measure of how long the reactive process takes.

### Sensor Data Processing with Traditional Blocking

```kotlin
 val startTime = System.currentTimeMillis()  
  
    // Simulate making 10 blocking calls to APIs  
  for (i in 1..10) {  
        val data = getSensorDataFromApiBlocking(i)  
        println(data)  
    }  
  
    val endTime = System.currentTimeMillis()  
    println("Blocking execution time: ${endTime - startTime} ms")  
}  
  
fun getSensorDataFromApiBlocking(id: Int): String {  
    // Simulating a blocking call to an external API  
  Thread.sleep(Random.nextLong(100, 200)) // Simulating network delay (blocking)  
  return "Sensor $id: Temperature ${Random.nextInt(20, 30)}°C"
```

Example output:
```
Sensor 1: Temperature 20°C
Sensor 2: Temperature 23°C
Sensor 3: Temperature 27°C
Sensor 4: Temperature 25°C
Sensor 5: Temperature 28°C
Sensor 6: Temperature 23°C
Sensor 7: Temperature 29°C
Sensor 8: Temperature 23°C
Sensor 9: Temperature 28°C
Sensor 10: Temperature 23°C
Blocking execution time: 1586 ms
```
In this example, the code simulates making 10 blocking API calls using a simple loop. Each call to `getSensorDataFromApiBlocking()` introduces a delay using `Thread.sleep()`, which simulates a network call that blocks the current thread. This ensures that each API call completes sequentially, and the program waits for the response before moving to the next one. The total execution time is measured by capturing the start and end times, demonstrating the inefficiency of blocking operations when multiple asynchronous calls are involved.

#### Performance overview

The blocking sensor operation took **1586 ms** because each API call is sequential and blocks the thread, causing delays to accumulate. In contrast, the reactive approach only took **306 ms**, as it allows concurrent execution of API calls, utilizing non-blocking operations to handle multiple requests efficiently.

### When not to use Reactive programming
```kotlin
data class User(val id: Int, val name: String, val email: String)  
  
class UserService {  
    fun getUserById(userId: Int): User {  
        // Simulate database call  
  println("Fetching user details for ID: $userId")  
        Thread.sleep(1000) // Simulating delay  
  return User(userId, "John Doe", "john.doe@example.com")  
    }  
}  
  
class ReportService {  
    fun generateUserReport(user: User): String {  
        // Simulate report generation  
  println("Generating report for user: ${user.name}")  
        Thread.sleep(1000) // Simulating delay  
  return "Report for ${user.name}"  
  }  
}  
  
fun main() {  
    val userService = UserService()  
    val reportService = ReportService()  
  
    // Fetch user details  
  val user = userService.getUserById(1)  
  
    // Generate user report  
  val report = reportService.generateUserReport(user)  
  
    println(report)  
}
```

This example demonstrates a simple use case where reactive programming is not necessary. The code simulates fetching user details from a database and generating a report, both of which involve sequential, blocking operations. Since the operations depend on each other (first fetching the user, then generating the report), using reactive programming here would introduce unnecessary complexity. In such cases, where operations are inherently synchronous and there is no need for parallelism or asynchronous execution, a straightforward, blocking approach is more efficient and easier to maintain.

