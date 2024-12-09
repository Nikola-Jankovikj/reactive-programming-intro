package SimpleExamples

import kotlin.random.Random

// --- Sensor data stream Blocking


fun main() {
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
}