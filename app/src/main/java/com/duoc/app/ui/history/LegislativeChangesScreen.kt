package com.duoc.app.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.duoc.app.data.model.DiffSegment
import com.duoc.app.data.model.LegislativeChange
import com.duoc.app.data.model.SegmentType

private val AddedBackground = Color(0xFFC8E6C9)
private val RemovedBackground = Color(0xFFFFCDD2)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegislativeChangesScreen(
    onBack: () -> Unit,
    viewModel: LegislativeChangesViewModel
) {
    val changes by viewModel.changes.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Legislative Changes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(changes, key = { it.id }) { change ->
                LegislativeChangeCard(change)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun LegislativeChangeCard(change: LegislativeChange) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(change.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${change.date} · ${change.description}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(Modifier.height(12.dp))

            Text(
                "Current version",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = buildHighlightedText(
                    segments = change.currentSegments,
                    highlightType = SegmentType.ADDED,
                    highlightColor = AddedBackground
                ),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Previous version",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = buildHighlightedText(
                    segments = change.previousSegments,
                    highlightType = SegmentType.REMOVED,
                    highlightColor = RemovedBackground
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun buildHighlightedText(
    segments: List<DiffSegment>,
    highlightType: SegmentType,
    highlightColor: Color
): AnnotatedString = buildAnnotatedString {
    segments.forEach { segment ->
        if (segment.type == highlightType) {
            withStyle(SpanStyle(background = highlightColor)) {
                append(segment.text)
            }
        } else {
            append(segment.text)
        }
    }
}
