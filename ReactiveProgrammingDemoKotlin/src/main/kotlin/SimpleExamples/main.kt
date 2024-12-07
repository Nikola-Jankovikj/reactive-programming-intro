package SimpleExamples

import io.reactivex.rxjava3.core.Observable

fun main() {
    val numbers = Observable.just(1, 2, 3, 4, 5)

    numbers
        .map { it * 2 }
        .filter { it > 5 }
        .subscribe(
            { println("Received: $it") },
            { error -> println("Error: ${error.message}") },
            { println("Stream completed!") }
        )
}