package SimpleExamples

// --- Sensor data stream reactive

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

fun main() {
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
        .delay(Random.nextLong(100, 200), TimeUnit.MILLISECONDS)  // Simulate network delay
}



