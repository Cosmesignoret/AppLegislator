package com.duoc.app.data.remote.dto

data class LegislativeChangeDto(
    val id: String,
    val title: String,
    val date: String,
    val description: String,
    val currentSegments: List<DiffSegmentDto>,
    val previousSegments: List<DiffSegmentDto>
)

data class DiffSegmentDto(
    val type: String,
    val text: String
)