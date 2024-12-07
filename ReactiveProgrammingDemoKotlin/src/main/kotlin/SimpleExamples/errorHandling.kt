package SimpleExamples

import io.reactivex.rxjava3.core.Observable

fun main() {
    val numbers = Observable.create<Int> { emitter ->
        emitter.onNext(1)
        emitter.onNext(2)
        emitter.onError(Exception("Something went wrong"))
        emitter.onNext(3) // This will not be emitted due to the error
        emitter.onComplete()
    }

    numbers
        .subscribe(
            { println("Received: $it") },
            { error -> println("Error handled: ${error.message}") },
            { println("Stream completed!") }
        )
}
