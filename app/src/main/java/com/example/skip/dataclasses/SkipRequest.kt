package com.example.skip.dataclasses

data class SkipRequest(val userId: String, val creatorId: String, val moderatorId: String, val dateStart: String, val dateEnd: String, val comment: String, val status: String, val reason: String, val fileInDean: Boolean, val fileUrl: List<String>)
