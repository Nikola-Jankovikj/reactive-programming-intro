package SimpleExamples

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

// Define a data class to represent stock updates
data class StockUpdate(
    val symbol: String,
    val price: Int,
    val timestamp: Long
)

fun main() {
    // Simulate a real-time stock price stream
    val stockUpdates = Observable.interval(1, TimeUnit.SECONDS)
        .map {
            val stockSymbol = listOf("GOOG", "AMZN", "MSFT").random() // Random stock symbols
            val stockPrice = Random.nextInt(50, 200) // Random stock price
            StockUpdate(stockSymbol, stockPrice, System.currentTimeMillis())

        }
        // Print the original data
        .doOnNext { println("Received: $it") }
        // Filter for stock prices greater than 100
        .filter { stockUpdate -> stockUpdate.price > 100 }
        // Transform data (e.g., add additional metadata)
        .map { stockUpdate -> stockUpdate.copy(symbol = "${stockUpdate.symbol} [Processed]") }

    // Subscribe to consume the stock updates
    stockUpdates.subscribe(
        { stockUpdate -> println("Processed: $stockUpdate") }, // OnNext
        { error -> println("Stream error: ${error.message}") }, // OnError
        { println("Stream completed") } // OnComplete
    )

    // Keep the main thread alive to observe the output
    Thread.sleep(20000)
}

