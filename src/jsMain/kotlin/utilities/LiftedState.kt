@file:Suppress("NOTHING_TO_INLINE")

package utilities

import react.StateInstance
import react.useState
import kotlin.reflect.KProperty

/**
 * Alternative to useState that returns an interface, thus enabling it to be passed around
 * without coupling to an implementation.
 * I find this kind of implementation very practical because most of the time you only pass the current value
 * of the state and a lambda allowing to set it to children components, and it allows both to be passed in a single
 * field and with much less writing on the parent component.
 * This can also be done using the StateInstance that useState returns, but then you are tying your implementation
 * to the StateInstance implementation which you can't intercept as it is a class from which you can't inherit.
 * LiftedState being an interface you can always create and pass your own implementation that implements
 * some logic on the getter or setter.
 */
fun <T> useLiftedState(initialValue: T): LiftedState<T> = LiftedStateImpl(useState(initialValue))

interface LiftedState<T> : LiftedStateSetter<T> {
    fun get(): T
}

interface LiftedStateSetter<T> {
    fun set(value: T)
    fun set(transform: (T) -> T)
}

var <T> LiftedState<T>.value
    get() = this.get()
    set(value) = this.set(value)

inline operator fun <T> LiftedState<T>.component1(): T = get()
inline operator fun <T> LiftedState<T>.component2(): LiftedStateSetter<T> = this
inline operator fun <T> LiftedState<T>.getValue(thisRef: Nothing?, property: KProperty<*>): T = get()
inline operator fun <T> LiftedState<T>.setValue(thisRef: Nothing?, property: KProperty<*>, value: T) = set(value)
inline operator fun <T> LiftedStateSetter<T>.invoke(value: T) = set(value)
inline operator fun <T> LiftedStateSetter<T>.invoke(noinline transform: (T) -> T) = set(transform)

private class LiftedStateImpl<T>(private val stateInstance: StateInstance<T>) : LiftedState<T> {
    override fun get(): T = stateInstance.component1()
    override fun set(transform: (T) -> T) = stateInstance.component2()(transform)
    override fun set(value: T) = stateInstance.component2()(value)
}