package com.example.kanjimemory.sharedComposables

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.example.kanjimemory.viewmodel.DragDropViewModel

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

@Composable
fun DragDropLogicScreen(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    /*
    DraggableScreen is wrapper around normal screen and makes it possible to drag and drop items and react
     */

    val state = remember { DragTargetInfo() }

    // We need current dragging state of dragging item
    CompositionLocalProvider(LocalDragTargetInfo provides state) {
        Box(modifier = modifier.fillMaxSize())
        {
            // content here is normal screen
            content()
            // check if user is currently dragging, then define targetSize
            if (state.isDragging) {
                var targetSize by remember { mutableStateOf(IntSize.Zero) }
                // Logic to increase the size of dragItem
                Box(modifier = Modifier
                    .graphicsLayer {
                        val offset = (state.dragPosition + state.dragOffset)
                        scaleX = 1.3f
                        scaleY = 1.3f
                        // alpha is to set transparency
                        alpha = if (targetSize == IntSize.Zero) 0f else .9f
                        translationX = offset.x.minus(targetSize.width / 2)
                        translationY = offset.y.minus(targetSize.height / 2)
                    }
                    .onGloballyPositioned { targetSize = it.size }
                ) {
                    // this is the copy of the draggable composable
                    state.draggableComposable?.invoke()
                }
            }
        }
    }
}

@Composable
fun <T> DragTarget(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    dragDropViewModel: DragDropViewModel,
    content: @Composable (() -> Unit)
) {

    /* Wrapper composable around content composable;
    A generic type of data can be carried with composable (dataToDrop), e.g., Kanji
    DragTarget contains all the info regarding the position of the dragged composable as it is shown in the UI

    currentState can be observed in DropItem, so that DropItem can detect that DragTarget is in correct
    position to get dropped -> this triggers a reaction from DropItem to show to user
     */
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    /*
    We need to get position of dragTarget, because initial position is used to calculate new position
    When we get drag changes, we can add them to current position and get right drag position ->
    get from onGloballyPositioned
     */
    Box(modifier = modifier
        .onGloballyPositioned { currentPosition = it.localToWindow(Offset.Zero) }
        .pointerInput(dataToDrop) {
            // define logic for long press gestures
            detectDragGestures(onDragStart = {
                // update viewModel
                dragDropViewModel.startDragging()
                currentState.dataToDrop = dataToDrop
                currentState.isDragging = true
                currentState.dragPosition = currentPosition + it
                // draggableComposable copies the composable!
                currentState.draggableComposable = content
            }, onDrag = { change, dragAmount ->
                change.consumeAllChanges()
                currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
            }, onDragEnd = {
                dragDropViewModel.stopDragging()
                currentState.dragOffset = Offset.Zero
                currentState.isDragging = false
            }, onDragCancel = {
                dragDropViewModel.stopDragging()
                currentState.dragOffset = Offset.Zero
                currentState.isDragging = false
            })
        }) {
        content()
    }
}

@Composable
fun <T> DropItem(
    modifier: Modifier,
    content: @Composable() (BoxScope.(isInBound: Boolean, data: T?) -> Unit)
) {

    /*
    Wrapper around drop target composable, i.e., the place where item should be dropped
    DropItem provides information: if DragItem is in bound, i.e., dropped in right place, and also
    provides the data
     */

    // get current dragTarget info, position, offset
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    // this checks if DragTarget is on top of DropItem
    var isCurrentDropTarget by remember { mutableStateOf(false) }

    Box(modifier = modifier.onGloballyPositioned {
        it.boundsInWindow().let { rect ->
            // check if rectangle contains DragTarget:
            // dragPosition = place where composable left off; dragOffset = place it got dragged to
            isCurrentDropTarget = rect.contains(dragPosition + dragOffset)
        }
    }) {
        /*
        We also need to check if item has been let go or if it is only hovering over drop zone
        if item to drop is on top of drop zone and it is not currently dragging, then we provide dataToDrop
         */
        val data = if (isCurrentDropTarget && !dragInfo.isDragging) dragInfo.dataToDrop as T? else null
        // invoke content composable:
        // content is the composable that is shown to user to know where item should be dropped
        // will provide isCurrentDropTarget for info if it is in bound, along with data
        content(isCurrentDropTarget, data)
    }
}

internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    //  to copy composable
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    // e.g., pass data to viewModel to process data
    var dataToDrop by mutableStateOf<Any?>(null)
}