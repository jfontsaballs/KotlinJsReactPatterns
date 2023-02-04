import react.StateInstance

fun <T> StateInstance<T>.get() = component1()
fun <T> StateInstance<T>.set(newValue: T) = component2()(newValue)
fun <T> StateInstance<T>.set(transform: (T) -> T) = component2()(transform)
