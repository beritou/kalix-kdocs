package com.example.domain

import kalix.scalasdk.valueentity.ValueEntity
import kalix.scalasdk.valueentity.ValueEntityContext
import com.example
import com.example.CurrentCounter
import com.example.DeleteCounter
import com.google.protobuf.empty.Empty
import io.grpc.Status.Code

class Counter(context: ValueEntityContext) extends AbstractCounter {

  override def emptyState: CounterState = CounterState()

  override def increase(
      currentState: CounterState,
      command: example.IncreaseValue
  ): ValueEntity.Effect[Empty] =
    if (command.value < 0) // (1)!
      effects.error(
        s"Increase requires a positive value. It was [${command.value}]."
      )
    else {
      val newState =
        currentState.copy(value = currentState.value + command.value)
      effects
        .updateState(newState) // (2)!
        .thenReply(Empty.defaultInstance) // (3)!
    }

  override def increaseWithConditional(
      currentState: CounterState,
      command: example.IncreaseValue
  ): ValueEntity.Effect[Empty] =
    if (command.value < 0)
      effects.error(
        s"Increase requires a positive value. It was [${command.value}].",
        Code.INVALID_ARGUMENT
      )
    else {
      if (commandContext.metadata.get("myKey").contains("myValue")) {
        val newState =
          currentState.copy(value = currentState.value + command.value * 2)
        effects
          .updateState(newState)
          .thenReply(Empty.defaultInstance)
      } else {
        val newState =
          currentState.copy(value = currentState.value + command.value)
        effects
          .updateState(newState)
          .thenReply(Empty.defaultInstance)
      }
    }

  override def decrease(
      currentState: CounterState,
      command: example.DecreaseValue
  ): ValueEntity.Effect[Empty] =
    if (command.value < 0)
      effects.error(
        s"Increase requires a positive value. It was [${command.value}]."
      )
    else
      effects
        .updateState(
          currentState.copy(value = currentState.value - command.value)
        )
        .thenReply(Empty.defaultInstance)

  override def reset(
      currentState: CounterState,
      command: example.ResetValue
  ): ValueEntity.Effect[Empty] =
    effects.updateState(CounterState()).thenReply(Empty.defaultInstance)

  override def getCurrentCounter(
      currentState: CounterState,
      command: example.GetCounter
  ): ValueEntity.Effect[example.CurrentCounter] =
    effects.reply(CurrentCounter(currentState.value))

  override def delete(
      currentState: CounterState,
      deleteCounter: DeleteCounter
  ): ValueEntity.Effect[Empty] =
    effects
      .deleteEntity()
      .thenReply(Empty.defaultInstance)
}
