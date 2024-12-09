package SimpleExamples

import io.reactivex.rxjava3.core.Observable

fun main() {
    val stream1 = Observable.just("A", "B", "C")
    val stream2 = Observable.just(1, 2, 3)

    Observable.zip(stream1, stream2) { s1, s2 -> "$s1$s2" }
        .subscribe(
            { println("Combined: $it") },
            { error -> println("Error: ${error.message}") },
            { println("Combination completed!") }
        )
}
