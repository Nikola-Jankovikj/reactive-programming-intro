package SimpleExamples

data class User(val id: Int, val name: String, val email: String)

class UserService {
    fun getUserById(userId: Int): User {
        // Simulate database call
        println("Fetching user details for ID: $userId")
        Thread.sleep(1000) // Simulating delay
        return User(userId, "John Doe", "john.doe@example.com")
    }
}

class ReportService {
    fun generateUserReport(user: User): String {
        // Simulate report generation
        println("Generating report for user: ${user.name}")
        Thread.sleep(1000) // Simulating delay
        return "Report for ${user.name}"
    }
}

fun main() {
    val userService = UserService()
    val reportService = ReportService()

    // Fetch user details
    val user = userService.getUserById(1)

    // Generate user report
    val report = reportService.generateUserReport(user)

    println(report)
}
