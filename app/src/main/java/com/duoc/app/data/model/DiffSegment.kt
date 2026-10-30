package com.duoc.app.data.model

enum class SegmentType { UNCHANGED, ADDED, REMOVED }

data class DiffSegment(
    val type: SegmentType,
    val text: String
)
