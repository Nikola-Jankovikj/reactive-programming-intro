
# Reactive Programming

## What is Reactive Programming?

Reactive programming is a declarative programming paradigm that focuses on responding to changes in data and events in real-time. At its core, it revolves around three key concepts:

- **Data Streams**: A continuous flow of data objects—ranging from zero to infinite—that at undetermined intervals travel from one point to another.
- **Functional Programming**: Declarative functions used to process, transform, and manipulate the data within these streams.
- **Asynchronous Observers**: Non-blocking mechanisms that allow code to execute efficiently without waiting for events to complete.

This paradigm is designed to handle dynamic and evolving systems by enabling asynchronous and non-blocking data processing. It excels in scenarios where the system needs to react instantly to events such as user inputs, live data feeds, or messages from other systems.

---

### Visual Representation of Data Streams

![alt text](https://miro.medium.com/v2/resize:fit:720/format:webp/0*DwzgTfD03WvqAx3Z.png)

Here is a visual representation of how data might flow. Here specifically we see a data stream with numbers, but it doesn’t need to be, it could be strings, floats, custom objects... Notice how they don’t come at the same intervals. The stream doesn’t serve data in predetermined intervals. The data can be served whenever. It may also contain 0, 1 or even an infinite amount of data. Next, we see that we can transform the data. The map function was used, multiplying every number by 2 and then they were filtered to numbers only larger than the number 5. It is best practice to use separate, simple functions chained together instead of complex functions. This is one example of how a data stream looks like and how the data can be changed.

**Example of Transformation:**
- A `map` function is applied to multiply each number by 2.
- The resulting data is filtered to include only numbers greater than 5.

**Best Practice:** Use simple, separate functions chained together rather than creating complex functions.

---

### Asynchronous vs. Synchronous Processing

![alt text](https://www.matthewyancer.com/assets/images/synchronous-vs-asynchronous.webp)

Now, we saw that the data doesn’t come right away. We might have to wait a long time for the next number to come, so there is no point blocking the other processes waiting for us to retrieve the data. Also, we do not know how many numbers we might see. What this essentially means is that the system can react to changes in data, without blocking the other processes waiting for the changes to happen. This is why reactive programming focuses on being asynchronous. The graphic above shows a visual representation of how asynchronous processing looks like compared to the synchronous processing you might be used to.

**Key takeaway:** Reactive programming focuses on asynchronous processing, where operations do not block other processes while waiting for data. This approach ensures systems can react to changes in data without halting other tasks.

---

### Back Pressure

![alt text](https://tech.io/servlet/fileservlet?id=79301389043122)

The idea of back pressure takes centre stage in circumstances where data flows through interconnected components like a river. When a fast-paced event producer engages with a slower subscriber, its function becomes even more important.

In reactive systems, back pressure serves as the conductor, maintaining the digital symphony's steady tempo. It allows subscribers to let publishers know when they should pause or slow down the pace of events. By preventing downstream components from being overloaded, this orchestration balances the system's processing speed and capacity.

**Key takeaway:** In order to create applications that are resilient, scalable, and responsive to changing data streams, reactive systems use back pressure to strike a careful balance.

---

## Publish/Subscribe Pattern

The publish/subscribe pattern is essentially the heart of reactive programming, where a publisher emits a stream of data, and a subscriber listens to and processes this stream. We may have multiple subscribers to the same publisher. This flow is "push-based," meaning the data is sent to the subscriber as soon as it becomes available. The subscriber can then transform, filter, or otherwise manipulate the data in real time. They may also opt out of listening for updates from the publisher. 

**In short:** The publish/subscribe pattern is central to reactive programming. In this model:
- A **publisher** emits a data stream.
- One or more **subscribers** listen to and process the data stream in real-time.

The flow is "push-based," meaning data is sent to subscribers as soon as it's available. Subscribers can transform, filter, or otherwise manipulate the data and can also unsubscribe from updates when needed.

### Difference from Streams API

Some of you might look at reactive code written in Java or Kotlin and it might seem the same as the Streams API. While here we are not going to go into detail in the specific implementations of the Rx libraries, we are going to mention that while Java Streams still represent a “stream” of data, usually the data is already in memory and the transformation happens synchronously, while here we do not have the data in memory, and everything happens asynchronously. Also, a big difference from the Streams API and the observer pattern is that here there are 3 channels:
- **Data channel:** This is where the data 
- **Error channel:** When there is an error it is sent through this channel. 
- **Complete channel:** This indicates when there is no more data coming through.

These channels let us very easily get the data, handle errors if there are any and unsubscribe if the stream is completed. 


**In short:** While the Streams API may seem similar, reactive programming differs in these ways:
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

It is always best to see it in practice to be able to better understand it. Here is a snippet in Kotlin of the same flow we showed above

### Kotlin Example

```kotlin
dataStream
    .map { it * 2 }
    .filter { it > 5 }
    .subscribe (
        { println("Received: $it") },
        { error -> println("Error: ${error.message}") },
        { println("Stream complete") }
    )
```

Here you can see that for each number, we first apply the map function, then the filter function and finally we subscribe to the changes. Notice how we use functional programming to achieve the transformation of our data. This means we use declarative programming with stateless functions. We chain these functions to achieve more complex transformations. This also simplifies our lives tremendously. Subscribe essentially means it will listen to the three channels. The first one is the data channel where the data arrives, next is the error channel so that we can handle any errors and finally the complete channel notifies us when the stream has finished.

- **`map`** and **`filter`** are functional programming constructs for data transformation.
- **`subscribe`** listens to three channels:
  - Data channel.
  - Error channel.
  - Complete channel.

### Imperative Code Comparison

Here is the same example using imperative programming:

```kotlin
try {
        for (number in numbers) {
            val transformed = number * 2
            transformedNumbers.add(transformed)
        }

        val filteredNumbers = mutableListOf<Int>()
        for (transformedNumber in transformedNumbers) {
            if (transformedNumber > 5) {
                filteredNumbers.add(transformedNumber)
            }
        }

        for (filteredNumber in filteredNumbers) {
            println("Received: $filteredNumber")
        }

        println("Stream completed!")
    } catch (error: Exception) {
        println("Error: ${error.message}")
    }
```
There is a lot more code. First it would take a lot more time just to read the code, next thing is how much more pleasant the declarative code is to look at. Now imagine having to chain more functions like this, it would be a mess! Declarative programming offers a cleaner, more efficient way to handle such transformations.

---

### Combining Multiple Streams

Reactive programming allows combining multiple streams and handling errors gracefully:

```kotlin
combinedStream
    .onErrorResume { println("Error occurred, stream ended") }
    .subscribe { println(it) }
```

This ensures errors don’t crash the application and provides proper handling while completing the stream.
