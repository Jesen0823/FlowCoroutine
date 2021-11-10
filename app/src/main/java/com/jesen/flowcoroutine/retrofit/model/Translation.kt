package com.jesen.flowcoroutine.retrofit.model

data class Translation(
    val `data`: Data,
    val result: Result
)

data class Data(
    val entries: List<TransEntry>,
    val language: String,
    val query: String,
    val type: String
)

data class Result(
    val code: Int,
    val msg: String
)

data class TransEntry(
    val entry: String,
    val explain: String
)