package com.example.myapplication

data class Quakes (
    val metadata: MetaData,
    val features: List<Features>
)

data class MetaData(
    val title: String,
    val count: Int

)

data class Features(
    val id: String,
    val properties: Property,
    val geometry: Geometry

)

data class Property(
    val magnitude: Float,
    val place: String,
    val title: String

)

data class Geometry(
    val coordinate: List<Float>
)
