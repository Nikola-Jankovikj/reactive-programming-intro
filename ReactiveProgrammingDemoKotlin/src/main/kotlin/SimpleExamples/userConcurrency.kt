package SimpleExamples

import io.reactivex.rxjava3.core.Observable

fun main() {

    Observable.range(1, 10000)
        .map {
            if (it == 5000) throw RuntimeException("Error: User $it caused a failure")
            "User $it connected"
        }
        .subscribe(
            { println(it) }, // onNext: process emitted items
            { error -> println("Error encountered: ${error.message}") }, // onError: handle the error
            { println("Completed successfully") } // onComplete: called when the stream finishes
        )
}
