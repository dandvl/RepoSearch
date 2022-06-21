package com.example.reposearch.data

data class Repo(
    val id : Int,
    val name : String,
    val full_name : String,
    val description : String,
    val url : String,
    val stargazers_count : Int,
    val html_url : String
)
