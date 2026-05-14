package com.example.algo

data class Experience(val name: String, var workDays: Int = 0) {
    fun increaseWorkDays() {
        workDays++
    }
}

typealias Employees = List<Experience>
