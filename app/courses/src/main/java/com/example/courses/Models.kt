package com.example.courses


data class ModelError(
    val error: String
)
data class ModelTag(
    val id: Int,
    val name: String
)
data class ModelCourse(
    val id: Int,
    val title: String,
    val tags: List<Int>,
    val cover: String,
    val price: Int
)
data class CourseData(
    val id: Int,
    val price: Int,
    val title: String,
    val tags: List<Int>,
    val description: String,
    val mentors: List<Mentor>,
    val cover: String,
    val plan: List<ModelTask>
)
data class Mentor(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val patronymic: String
)
data class ModelTask(
    val title: String,
    val description: String,
    val duration: Int
)