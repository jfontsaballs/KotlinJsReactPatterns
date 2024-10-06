import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import utilities.LiftedState
import utilities.useLiftedState
import web.html.InputType

/**
 * This is a showcase of using components inside other components and lifting state
 */
val ComponentHierarchyExample = FC<Props> {
    // In this example, the name needs to be known by both NameDisplay and NameQuery components, so we
    // must lift it to the container of all components that need it
    var name by useState("default")
    // With the surname state we are going to showcase another way of letting a child component edit its value
    val surnameState = useLiftedState("aaa")

    // We then pass the name to the component
    MyDisplay {
        // We must use this here, to differentiate the NameDisplayProps property from the state variable
        // As surname is not declared using by, we must call get on it
        this.content = "$name ${surnameState.get()}"
    }

    // In this case the component needs to know the current value as well as modify it, so we
    // need to pass a lambda to allow modifications to flow up
    MyQuery {
        this.content = name
        this.setContent = { newValue -> name = newValue }
    }

    // We could reuse the NameQuery component, but I want to show different ways of passing state down
    MyQuery2 { this.contentState = surnameState }
}

// Component properties must always be declared as an external interface,
// which must inherit from Props built-in interface.
// All properties must be var's (writeable), not val's (read-only)
external interface NameDisplayProps : Props {
    var content: String
}

val MyDisplay = FC<NameDisplayProps> { props ->
    // In Javascript, we would return content (JSX) from a function component for it to be rendered
    // In Kotlin Wrappers they allow for content to be added throughout the function. This is accomplished
    // via the ChildrenBuilder context that every functional component is provided. This also makes the JSX
    // fragment unnecessary.
    // - HTML components and React components are added to the parent component when they are invoked; the
    //   ChildrenBuilder interface contains an invoke operator that extends the ElementType interface. This
    //   interface instantiates the component, and it adds it to the parent component contents.
    // - Strings (content that goes inside tags) are added using the unary plus operator, which is also
    //   defined on strings in the ChildrenBuilder interface
    div {
        +"Hello, ${props.content}"
        @Suppress("UNUSED_EXPRESSION")
        "This is not rendered in HTML"
    }

    // Remember to always invoke every component you use, because if you forget it won't be rendered
    // This is an empty div
    div()

    // Another empty div
    div {}

    // This does not render to HTML because it's not invoked
    // Check it using the inspector
    div
}

external interface NameQueryProps : Props {
    var content: String
    var setContent: (String) -> Unit
}

val MyQuery = FC<NameQueryProps> { props ->
    input {
        // We might combine css classes declared in code with inline css properties
        type = InputType.text
        value = props.content
        onChange = { event -> props.setContent(event.target.value) }
    }

    // Pitfalls to be aware of:
    // - Using name instead of props.name refers to the name property of the input object
    // - Trying to assign to props.name does nothing, we must use props.setName
}

// In this case we pass an object that allows both getting and setting the state, instead
// of passing the value and a setter separately
external interface NameQuery2Props : Props {
    var contentState: LiftedState<String>
}

val MyQuery2 = FC<NameQuery2Props> { props ->
    input {
        // We might combine css classes declared in code with inline css properties
        type = InputType.text
        value = props.contentState.get()
        onChange = { event -> props.contentState.set(event.target.value) }
    }

    // Pitfalls to be aware of:
    // - Using name instead of props.name refers to the name property of the input object
    // - Trying to assign to props.name does nothing, we must use props.setName
}