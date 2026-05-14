package com.example.algo

fun main() {
    val employees =
        listOf(
            Experience("Alice", 0),
            Experience("Bob", 1),
            Experience("Charlie", 998),
            Experience("David", 1012),
            Experience("Eve", 2500),
            Experience("Frank", 5000),
            Experience("Grace", 987),
            Experience("Heidi", 990),
            Experience("Ivan", 997),
            Experience("Judy", 1003),
        )

    val result = selection(employees)
    println(result)
}
