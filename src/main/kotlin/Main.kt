import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Interface
interface Bookable {
    fun book(): String
}

// Sealed class for booking results
sealed class BookingResult {
    data class Confirmed(val message: String) : BookingResult()
    data class Rejected(val reason: String) : BookingResult()
}

// Data class
data class Movie(
    val movieId: Int,
    val title: String,
    val genre: String,
    val ticketPrice: Double
)

// Parent class
open class Customer(
    val customerName: String
) {
    open fun getDiscount(): Double = 0.0
}

// Inheritance
class VipCustomer(customerName: String) : Customer(customerName) {

    override fun getDiscount(): Double = 0.15
}

// Class implementing an interface
class CinemaMovie(
    val movie: Movie
) : Bookable {

    override fun book(): String {
        return "Ticket for '${movie.title}' booked successfully."
    }
}

// Main application class
class Cinema {

    // List
    private val movieList = mutableListOf(
        CinemaMovie(
            Movie(101, "Interstellar", "Sci-Fi", 12.50)
        ),
        CinemaMovie(
            Movie(102, "The Grand Budapest Hotel", "Comedy", 9.50)
        ),
        CinemaMovie(
            Movie(103, "Inception", "Sci-Fi", 11.00)
        ),
        CinemaMovie(
            Movie(104, "The Dark Knight", "Action", 13.00)
        ),
        CinemaMovie(
            Movie(105, "The Green Mile", "Drama", 10.00)
        ),
        CinemaMovie(
            Movie(106, "Spirited Away", "Animation", 8.50)
        )
    )

    // Set
    private val availableGenres: Set<String>
        get() = movieList
            .map { it.movie.genre }
            .toSet()

    // Map
    private val availableSeats: Map<Int, Int> = mapOf(
        101 to 20,
        102 to 15,
        103 to 12,
        104 to 18,
        105 to 10,
        106 to 25
    )

    // Display all movies
    fun displayMovies() {

        println("\n========== MOVIE CATALOG ==========")

        for (cinemaMovie in movieList) {

            val movie = cinemaMovie.movie
            val seats = availableSeats[movie.movieId] ?: 0

            println(
                "${movie.movieId}. ${movie.title} | " +
                        "${movie.genre} | " +
                        "$${movie.ticketPrice} | " +
                        "Seats: $seats"
            )
        }
    }

    // map + filter
    fun findMovies(searchText: String): List<Movie> {

        return movieList
            .map { it.movie }
            .filter { movie ->
                movie.title.contains(
                    searchText,
                    ignoreCase = true
                ) ||
                        movie.genre.contains(
                            searchText,
                            ignoreCase = true
                        )
            }
    }

    // reduce
    fun calculateTotalCatalogPrice(): Double {

        return movieList
            .map { it.movie.ticketPrice }
            .reduce { total, price ->
                total + price
            }
    }

    // Demonstrating Set
    fun displayGenres() {

        println("\nAvailable genres:")

        availableGenres.forEach { genre ->
            println("- $genre")
        }
    }

    // Find a movie by ID
    fun findMovie(movieId: Int): CinemaMovie? {

        return movieList.find {
            it.movie.movieId == movieId
        }
    }

    // Higher-order function
    fun processMovies(
        condition: (Movie) -> Boolean,
        operation: (Movie) -> Unit
    ) {

        movieList
            .map { it.movie }
            .filter(condition)
            .forEach(operation)
    }

    // Suspend function
    suspend fun makeBooking(
        selectedMovie: CinemaMovie,
        customer: Customer
    ): BookingResult {

        println(
            "\nChecking availability for " +
                    "'${selectedMovie.movie.title}'..."
        )

        // Simulate server/database delay
        delay(1000)

        val seats =
            availableSeats[selectedMovie.movie.movieId] ?: 0

        return if (seats > 0) {

            val discount = customer.getDiscount()

            val finalPrice =
                selectedMovie.movie.ticketPrice *
                        (1 - discount)

            BookingResult.Confirmed(
                "${selectedMovie.book()} " +
                        "Customer: ${customer.customerName}. " +
                        "Final price: $" +
                        "%.2f".format(finalPrice)
            )

        } else {

            BookingResult.Rejected(
                "There are no seats available for " +
                        "'${selectedMovie.movie.title}'."
            )
        }
    }
}

// Program entry point
fun main() = runBlocking {

    val cinema = Cinema()

    val normalCustomer =
        Customer("Daniel")

    val vipCustomer =
        VipCustomer("Michael")

    println("======================================")
    println("       CINEMA BOOKING SYSTEM")
    println("======================================")

    // Display List
    cinema.displayMovies()

    // Display Set
    cinema.displayGenres()

    // Demonstrate reduce
    val catalogPrice =
        cinema.calculateTotalCatalogPrice()

    println(
        "\nTotal price of all movie tickets: " +
                "$${"%.2f".format(catalogPrice)}"
    )

    // Demonstrate filter + map
    println("\nSearch results for 'sci-fi':")

    val foundMovies =
        cinema.findMovies("sci-fi")

    foundMovies.forEach { movie ->
        println(
            "${movie.title} - $${movie.ticketPrice}"
        )
    }

    // Demonstrate higher-order function and lambda
    println("\nMovies cheaper than $10:")

    cinema.processMovies(
        condition = { movie ->
            movie.ticketPrice < 10.0
        },
        operation = { movie ->
            println(
                "${movie.title}: " +
                        "$${movie.ticketPrice}"
            )
        }
    )

    // Demonstrate inheritance and polymorphism
    val customers: List<Customer> =
        listOf(
            normalCustomer,
            vipCustomer
        )

    println("\nCustomer discounts:")

    customers.forEach { customer ->

        println(
            "${customer.customerName}: " +
                    "${customer.getDiscount() * 100}%"
        )
    }

    // Demonstrate coroutine and suspend function
    println("\n========== BOOKING ==========")

    val chosenMovie =
        cinema.findMovie(101)

    if (chosenMovie != null) {

        val bookingJob = launch {

            val result =
                cinema.makeBooking(
                    selectedMovie = chosenMovie,
                    customer = vipCustomer
                )

            // Demonstrate sealed class + when
            when (result) {

                is BookingResult.Confirmed -> {
                    println(
                        "SUCCESS: ${result.message}"
                    )
                }

                is BookingResult.Rejected -> {
                    println(
                        "FAILED: ${result.reason}"
                    )
                }
            }
        }

        bookingJob.join()
    }

    println("\nApplication finished successfully.")
}
