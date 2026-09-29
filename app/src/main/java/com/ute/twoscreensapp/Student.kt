package com.ute.twoscreensapp

import java.io.Serializable

data class Student(
    val id: String,
    val name: String,
    val gpa: Double
) : Serializable