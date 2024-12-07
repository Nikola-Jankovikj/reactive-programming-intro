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

fun imperativeExample() {
    val numbers = listOf(1, 2, 3, 4, 5)

    val transformedNumbers = mutableListOf<Int>()


    try {
        // Transform each number by multiplying it by 2
        for (number in numbers) {
            val transformed = number * 2
            transformedNumbers.add(transformed)
        }

        // Filter out numbers less than or equal to 5
        val filteredNumbers = mutableListOf<Int>()
        for (transformedNumber in transformedNumbers) {
            if (transformedNumber > 5) {
                filteredNumbers.add(transformedNumber)
            }
        }

        // Print each filtered number
        for (filteredNumber in filteredNumbers) {
            println("Received: $filteredNumber")
        }

        // Indicate completion of processing
        println("Stream completed!")
    } catch (error: Exception) {
        // Handle any errors
        println("Error: ${error.message}")
    }
}
