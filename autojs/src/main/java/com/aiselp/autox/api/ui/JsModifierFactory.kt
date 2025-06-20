package com.aiselp.autox.api.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aiselp.autox.api.ui.component.parseColor
import com.aiselp.autox.api.ui.component.parseFloat
import com.aiselp.autox.engine.EventLoopQueue
import com.caoccao.javet.annotations.V8Function
import com.caoccao.javet.values.V8Value
import com.caoccao.javet.values.reference.V8ValueArray
import com.caoccao.javet.values.reference.V8ValueFunction

private const val TAG = "JsModifierFactory"

private typealias ExtOwner = @Composable ((modifier: Modifier, args: List<Any?>) -> Modifier)?
private typealias ExtRow = @Composable (RowScope.(modifier: Modifier, args: List<Any?>) -> Modifier)?
private typealias ExtColumn = @Composable (ColumnScope.(modifier: Modifier, args: List<Any?>) -> Modifier)?


class ModifierExtFactory(private val eventLoopQueue: EventLoopQueue) {

    private val modifierExts = buildMap<String, ModifierExtBuilder> {
        put("fillMaxSize", newExt { modifier, _ -> modifier.fillMaxSize() })
        put("fillMaxWidth", newExt { modifier, _ -> modifier.fillMaxWidth() })
        put("fillMaxHeight", newExt { modifier, _ -> modifier.fillMaxHeight() })
        put("background", newExt { modifier, args ->
            val t = args.getOrNull(0) ?: return@newExt modifier
            if (t == "theme") {
                return@newExt modifier.background(MaterialTheme.colorScheme.background)
            }
            val color = parseColor(t)
            if (color != null) {
                modifier.background(color)
            } else modifier
        })
        put("weight", object : ModifierExtBuilder() {
            override var RowExt: ExtRow = { modifier, args ->
                val i = parseFloat(args.getOrNull(0))
                modifier.weight(i ?: 1f)
            }
            override var ColumnExt: ExtColumn = { modifier, args ->
                val i = parseFloat(args.getOrNull(0))
                modifier.weight(i ?: 1f)
            }
        })
        put("width", newExt { modifier, args ->
            val width = parseFloat(args.getOrNull(0))
            if (width != null) modifier.width(width.dp) else modifier
        })
        put("height", newExt { modifier, args ->
            val height = parseFloat(args.getOrNull(0))
            if (height != null) modifier.height(height.dp) else modifier
        })
        put("rotate", newExt { modifier, args ->
            val rotate = parseFloat(args.getOrNull(0))
            if (rotate != null) modifier.rotate(rotate) else modifier
        })
        put("padding", newExt { modifier, args ->
            val left = parseFloat(args.getOrNull(0)) ?: return@newExt modifier
            val top = parseFloat(args.getOrNull(1)) ?: return@newExt modifier
            val right = parseFloat(args.getOrNull(2)) ?: return@newExt modifier
            val bottom = parseFloat(args.getOrNull(3)) ?: return@newExt modifier
            modifier.padding(left.dp, top.dp, right.dp, bottom.dp)
        })
        put("clickable", newExt { modifier, args ->
            println("clickable: $args")
            val fn = (args.getOrNull(0) as? EventLoopQueue.V8Callback) ?: return@newExt modifier
            modifier.clickable { fn.invoke() }
        })
        put("verticalScroll", newExt { modifier, _ ->
            modifier.verticalScroll(rememberScrollState())
        })
        put("horizontalScroll", newExt { modifier, _ ->
            modifier.horizontalScroll(rememberScrollState())
        })
        put("widthIn", newExt { modifier, args ->
            val min = parseFloat(args.getOrNull(0))
            val max = parseFloat(args.getOrNull(1))
            modifier.widthIn(
                min?.dp ?: Dp.Unspecified,
                max?.dp ?: Dp.Unspecified
            )
        })
        put("heightIn", newExt { modifier, args ->
            val min = parseFloat(args.getOrNull(0))
            val max = parseFloat(args.getOrNull(1))
            modifier.heightIn(
                min?.dp ?: Dp.Unspecified,
                max?.dp ?: Dp.Unspecified
            )
        })
    }

    fun getModifierExtBuilder(key: String): ModifierExtBuilder? {
        return modifierExts[key]
    }

    fun newRowExt(r: ExtRow) = ModifierExtBuilder().apply { RowExt = r }
    fun newColumnExt(r: ExtColumn) = ModifierExtBuilder().apply { ColumnExt = r }
    fun newExt(r: ExtOwner) = ModifierExtBuilder().apply { Ext = r }

    open inner class ModifierExtBuilder {
        open var Ext: ExtOwner = null
        open var RowExt: ExtRow = null
        open var ColumnExt: ExtColumn = null

        @V8Function
        fun createModifierExt(arr: V8ValueArray): ModifierExt {
            val converter = arr.v8Runtime.converter
            val args = mutableListOf<Any?>()
            arr.forEach<V8Value, V8Value, RuntimeException> { _, value ->
                if (value is V8ValueFunction) {
                    args.add(eventLoopQueue.createV8Callback(value))
                } else args.add(converter.toObject(value))
            }
            return object : ModifierExt {
                override val Ext: @Composable (modifier: Modifier) -> Modifier =
                    { this@ModifierExtBuilder.Ext?.invoke(it, args) ?: it }
                override val RowExt: @Composable (RowScope.(modifier: Modifier) -> Modifier) =
                    { this@ModifierExtBuilder.Ext?.invoke(it, args) ?: Ext.invoke(it) }
                override val ColumnExt: @Composable (ColumnScope.(modifier: Modifier) -> Modifier) =
                    { this@ModifierExtBuilder.ColumnExt?.invoke(this, it, args) ?: Ext.invoke(it) }

            }
        }
    }

    interface ModifierExt {
        val Ext: @Composable (modifier: Modifier) -> Modifier
        val RowExt: @Composable (RowScope.(modifier: Modifier) -> Modifier)
        val ColumnExt: @Composable (ColumnScope.(modifier: Modifier) -> Modifier)
    }
}