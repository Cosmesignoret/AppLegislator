package com.duoc.app.data.model

data class LegislativeChange(
    val id: String,
    val title: String,
    val date: String,
    val description: String,
    val currentSegments: List<TextSegment>,
    val previousSegments: List<TextSegment>
)
