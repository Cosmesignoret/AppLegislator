package com.duoc.app.data.repository

import com.duoc.app.data.model.DiffSegment
import com.duoc.app.data.model.LegislativeChange
import com.duoc.app.data.model.SegmentType
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
                DiffSegment(SegmentType.UNCHANGED, "Once upon a time there was a young "),
                DiffSegment(SegmentType.ADDED, "Platypus"),
                DiffSegment(SegmentType.UNCHANGED, " named Zapi who lived at the edge of a farm. Since he was little he had felt an "),
                DiffSegment(SegmentType.ADDED, "enormous fascination"),
                DiffSegment(SegmentType.UNCHANGED, " for the "),
                DiffSegment(SegmentType.ADDED, "sheep"),
                DiffSegment(SegmentType.UNCHANGED, ". He loved watching them "),
                DiffSegment(SegmentType.ADDED, "graze"),
                DiffSegment(SegmentType.UNCHANGED, ".")
            ),
            previousSegments = listOf(
                DiffSegment(SegmentType.UNCHANGED, "Once upon a time there was a young "),
                DiffSegment(SegmentType.REMOVED, "Rabbit"),
                DiffSegment(SegmentType.UNCHANGED, " named Zapi who lived at the edge of a farm. Since he was little he had felt a "),
                DiffSegment(SegmentType.REMOVED, "great admiration"),
                DiffSegment(SegmentType.UNCHANGED, " for the "),
                DiffSegment(SegmentType.REMOVED, "hens"),
                DiffSegment(SegmentType.UNCHANGED, ". He loved watching them "),
                DiffSegment(SegmentType.REMOVED, "walk in groups"),
                DiffSegment(SegmentType.UNCHANGED, ".")
            )
        ),
        LegislativeChange(
            id = "2",
            title = "Sample Article 2 - Rural Heritage Act",
            date = "May 4, 2026",
            description = "Adjustment of setting and action",
            currentSegments = listOf(
                DiffSegment(SegmentType.UNCHANGED, "The "),
                DiffSegment(SegmentType.ADDED, "old mill"),
                DiffSegment(SegmentType.UNCHANGED, " stood quietly by the "),
                DiffSegment(SegmentType.ADDED, "river"),
                DiffSegment(SegmentType.UNCHANGED, ", and every evening a "),
                DiffSegment(SegmentType.ADDED, "small group of farmers"),
                DiffSegment(SegmentType.UNCHANGED, " would gather to "),
                DiffSegment(SegmentType.ADDED, "share stories"),
                DiffSegment(SegmentType.UNCHANGED, ".")
            ),
            previousSegments = listOf(
                DiffSegment(SegmentType.UNCHANGED, "The "),
                DiffSegment(SegmentType.REMOVED, "barn"),
                DiffSegment(SegmentType.UNCHANGED, " stood quietly by the "),
                DiffSegment(SegmentType.REMOVED, "road"),
                DiffSegment(SegmentType.UNCHANGED, ", and every evening a "),
                DiffSegment(SegmentType.REMOVED, "lonely traveler"),
                DiffSegment(SegmentType.UNCHANGED, " would gather to "),
                DiffSegment(SegmentType.REMOVED, "rest"),
                DiffSegment(SegmentType.UNCHANGED, ".")
            )
        )
    )

    override fun getChanges(): Flow<List<LegislativeChange>> = flowOf(changes)
}
