# mobile-hw1
# Cinema Booking System

A small Kotlin/JVM console application that demonstrates basic and intermediate Kotlin programming concepts through a simple cinema movie booking system.

The application allows users to view movies, search by title or genre, display available genres, calculate the total catalog price, and simulate a movie booking with a VIP discount.

## How to Run

### Requirements

* JDK 17 or later
* Gradle
* Kotlin/JVM
* IntelliJ IDEA or VS Code

### Run with Gradle

Open a terminal in the project directory and run:

```bash
./gradlew run
```

The application will start in the terminal.

## Homework Requirements

The main Kotlin requirements are demonstrated in `src/main/kotlin/Main.kt`:

* **Variables and data types** — movie IDs, names, prices, customers, and seat counts.
* **Conditions** — `if` and `when` statements are used for booking and result handling.
* **Loops** — `for` and `forEach` are used to process collections.
* **List** — `movieList` stores the available movies.
* **Set** — `availableGenres` stores unique movie genres.
* **Map** — `availableSeats` associates movie IDs with available seats.
* **Collection operations** — `map()`, `filter()`, and `reduce()` are used for searching, filtering, transforming, and calculating prices.
* **Functions** — the application is divided into multiple functions such as `findMovies()`, `displayMovies()`, and `makeBooking()`.
* **Higher-order functions and lambdas** — `processMovies()` accepts functions as parameters and is called with lambda expressions.
* **Classes and objects** — `Cinema`, `Movie`, `Customer`, `VipCustomer`, and `CinemaMovie` demonstrate classes and object creation.
* **Inheritance** — `VipCustomer` inherits from `Customer`.
* **Interfaces and polymorphism** — `Bookable` is implemented by `CinemaMovie`, while `Customer` and `VipCustomer` demonstrate polymorphism.
* **Data class** — `Movie` is declared as a data class.
* **Sealed class** — `BookingResult` represents successful and failed booking results.
* **Coroutines** — `launch`, `runBlocking`, and `delay()` are used to simulate asynchronous booking processing.
* **Suspend function** — `makeBooking()` is declared as a `suspend` function.

The project is a console application and does not use Android components such as Activities, Fragments, or Jetpack Compose.
