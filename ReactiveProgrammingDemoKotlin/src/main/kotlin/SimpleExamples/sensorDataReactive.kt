package SimpleExamples

// --- Sensor data stream reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import kotlin.random.Random

fun main() {
    val startTime = System.currentTimeMillis()

    // Simulate making asynchronous calls to 10 APIs (e.g., fetching sensor data from a remote service)
    val sensorDataStream = Flux.range(1, 10)
        .flatMap { getSensorDataFromApiReactive(it) }
        .doOnNext { println(it) }

    sensorDataStream.blockLast()

    val endTime = System.currentTimeMillis()
    println("Reactive execution time: ${endTime - startTime} ms")
}

fun getSensorDataFromApiReactive(id: Int): Mono<String> {
    // Simulating an asynchronous call to an external API
    return Mono.just("Sensor $id: Temperature ${Random.nextInt(20, 30)}°C")
        .delayElement(Duration.ofMillis(Random.nextLong(100, 200)))  // Simulate network delay
}


