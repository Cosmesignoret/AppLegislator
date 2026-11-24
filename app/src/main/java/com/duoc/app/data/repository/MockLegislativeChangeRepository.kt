package com.duoc.app.data.repository

import com.duoc.app.data.model.LegislativeChange
import com.duoc.app.data.model.SegmentType
import com.duoc.app.data.model.TextSegment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockLegislativeChangeRepository : LegislativeChangeRepository {

    private val changes = listOf(
        LegislativeChange(
            id = "1",
            title = "Sample Article 1 - Farm Animals Act",
            date = "March 12, 2026",
            description = "Change of protagonist and preferences",
            currentSegments = listOf(
                TextSegment(SegmentType.UNCHANGED, "Once upon a time there was a young "),
                TextSegment(SegmentType.ADDED, "Platypus"),
                TextSegment(SegmentType.UNCHANGED, " named Zapi who lived at the edge of a farm. Since he was little he had felt an "),
                TextSegment(SegmentType.ADDED, "enormous fascination"),
                TextSegment(SegmentType.UNCHANGED, " for the "),
                TextSegment(SegmentType.ADDED, "sheep"),
                TextSegment(SegmentType.UNCHANGED, ". He loved watching them "),
                TextSegment(SegmentType.ADDED, "graze"),
                TextSegment(SegmentType.UNCHANGED, ".")
            ),
            previousSegments = listOf(
                TextSegment(SegmentType.UNCHANGED, "Once upon a time there was a young "),
                TextSegment(SegmentType.REMOVED, "Rabbit"),
                TextSegment(SegmentType.UNCHANGED, " named Zapi who lived at the edge of a farm. Since he was little he had felt a "),
                TextSegment(SegmentType.REMOVED, "great admiration"),
                TextSegment(SegmentType.UNCHANGED, " for the "),
                TextSegment(SegmentType.REMOVED, "hens"),
                TextSegment(SegmentType.UNCHANGED, ". He loved watching them "),
                TextSegment(SegmentType.REMOVED, "walk in groups"),
                TextSegment(SegmentType.UNCHANGED, ".")
            )
        ),
        LegislativeChange(
            id = "2",
            title = "Sample Article 2 - Rural Heritage Act",
            date = "May 4, 2026",
            description = "Adjustment of setting and action",
            currentSegments = listOf(
                TextSegment(SegmentType.UNCHANGED, "The "),
                TextSegment(SegmentType.ADDED, "old mill"),
                TextSegment(SegmentType.UNCHANGED, " stood quietly by the "),
                TextSegment(SegmentType.ADDED, "river"),
                TextSegment(SegmentType.UNCHANGED, ", and every evening a "),
                TextSegment(SegmentType.ADDED, "small group of farmers"),
                TextSegment(SegmentType.UNCHANGED, " would gather to "),
                TextSegment(SegmentType.ADDED, "share stories"),
                TextSegment(SegmentType.UNCHANGED, ".")
            ),
            previousSegments = listOf(
                TextSegment(SegmentType.UNCHANGED, "The "),
                TextSegment(SegmentType.REMOVED, "barn"),
                TextSegment(SegmentType.UNCHANGED, " stood quietly by the "),
                TextSegment(SegmentType.REMOVED, "road"),
                TextSegment(SegmentType.UNCHANGED, ", and every evening a "),
                TextSegment(SegmentType.REMOVED, "lonely traveler"),
                TextSegment(SegmentType.UNCHANGED, " would gather to "),
                TextSegment(SegmentType.REMOVED, "rest"),
                TextSegment(SegmentType.UNCHANGED, ".")
            )
        )
    )

    override fun getChanges(): Flow<List<LegislativeChange>> = flowOf(changes)
}