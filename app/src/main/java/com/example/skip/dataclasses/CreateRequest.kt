package com.example.skip.dataclasses

data class CreateRequest(val userId: String, val dateStart: String, val dateEnd: String, val comment: String, val status: String, val reason: String, val fileInDean: Boolean, val fileUrl: List<String>)
