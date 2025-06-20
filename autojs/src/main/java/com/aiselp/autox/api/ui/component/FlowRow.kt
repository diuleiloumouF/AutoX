package com.aiselp.autox.api.ui.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aiselp.autox.api.ui.ComposeElement
import com.aiselp.autox.api.ui.RenderRow

internal object FlowRow : VueNativeComponent {
    override val tag: String = "FlowRow"

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Render(
        modifier: Modifier,
        element: ComposeElement,
        content: @Composable () -> Unit
    ) {
        val maxItemsInEachRow = element.props["maxItemsInEachRow"] as? Int
        FlowRow(
            modifier = modifier,
            verticalArrangement = parseVerticalArrangement(element.props["verticalArrangement"] as? String),
            horizontalArrangement = parseHorizontalArrangement(element.props["horizontalArrangement"] as? String),
            maxItemsInEachRow = maxItemsInEachRow ?: Int.MAX_VALUE
        ) {
            element.children.forEach {
                RenderRow(it)
            }
        }
    }
}