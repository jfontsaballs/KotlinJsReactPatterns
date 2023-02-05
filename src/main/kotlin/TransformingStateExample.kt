import react.VFC
import react.dom.html.ReactHTML
import react.useState

val TransformingStateExample = VFC {
    // That's another way of using state, age contains the current value and setAge is a function
    // that can be passed the new value or a lambda to calculate it
    val (age, setAge) = useState(0)

    // Remember that to show a string we need the unary plus operator
    +age.toString()

    ReactHTML.button {
        +"Increment"
        // When we need the old value of the state to calculate the new value, we MUST use
        // a lambda.
        onClick = { setAge { currentValue -> currentValue + 1 } }

        //region Example of incorrect code
        @Suppress("ConstantConditionIf")
        if (false) { // We render incorrect code inside if(false) to prevent it from executing
            onClick = { setAge(age + 1) } // That would be incorrect and in certain circumstances may fail to increment
        }
        //endregion
    }

    ReactHTML.button {
        +"Decrement"
        onClick = { setAge { it - 1 } }
    }
}