import emotion.react.css
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.ReactHTML.span
import react.useMemo
import web.cssom.Color
import web.cssom.NamedColor
import web.cssom.px

external interface MyGenericComponentProps<T : Any> : Props {
    var item: T
    var colorGetter: (T) -> Color
}

// To create a generic component we need to use a factory, as properties (val's) do not accept generic arguments
fun <T : Any> myGenericComponentFactory() = FC<MyGenericComponentProps<T>> { props ->
    span {
        css {
            background = props.colorGetter(props.item)
            margin = 2.px
            color = NamedColor.white
        }
        +props.item.toString()
    }
}

// This is the same code reuse technique used in Main. As it is a normal function, it allows generics to be used.
// BEWARE that hooks can not be used in here, as it is not a React component.
// The Kotlin Wrappers development team discourages this pattern, but I find it very useful when generics
// are involved but no hooks are needed
fun <T : Any> ChildrenBuilder.myGenericNonComponent(value: T, colorGetter: (T) -> Color) {
    span {
        css {
            background = colorGetter(value)
            margin = 2.px
            color = NamedColor.white
        }
        +value.toString()
    }
}

// Then we need to instantiate the generic component by concreting the class.
// We can do it outside
val MyGenericComponentForInt = myGenericComponentFactory<Int>()
val GenericComponentExample = FC<Props> {
    // Or inside the component by memoizing it
    val MyGenericComponentForString = useMemo { myGenericComponentFactory<String>() }
    MyGenericComponentForInt {
        item = 3
        colorGetter = { if (it > 5) NamedColor.red else NamedColor.blue }
    }
    MyGenericComponentForString {
        item = "patata"
        colorGetter = { if (it.length > 5) NamedColor.red else NamedColor.blue }
    }
    myGenericNonComponent("mongeta") { if (it.length > 5) NamedColor.red else NamedColor.blue }

    // Never do this, it is a huge performance killer
    (myGenericComponentFactory<String>()) {
        item = "aaa"
        colorGetter = { if (it.length > 5) NamedColor.red else NamedColor.blue }
    }
}