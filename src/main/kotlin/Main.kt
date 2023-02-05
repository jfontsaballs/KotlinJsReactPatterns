import csstype.px
import emotion.react.css
import react.ChildrenBuilder
import react.VFC
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import web.dom.document

fun main() {
    val container = document.createElement("div")
    document.body.appendChild(container)

    createRoot(container).render(root.create())
}

// VFC creates a component without properties
// Be very careful to always import HTML elements from the react.dom.html.ReactHTML namespace
// In Kotlin, there are other implementations of HTML elements available under different namespaces,
// however, when using react only those in the correct namespace will work.
val root = VFC {
    div {
        FormattingExample {
            name = "MyName"
        }
    }

    renderSeparator()

    div {
        // When using a component without props, do not forget calling it either with parentheses or an empty lambda
        ComponentHierarchyExample() // Or ComponentHierarchyExample{}
    }

    renderSeparator()

    div {
        TransformingStateExample {}
    }

    renderSeparator()

    BasicListExample {
        myItems = listOf("patata", "mongeta", "carbassó")
    }

    renderSeparator()

    ComponentWithChildrenExample {
        myItems = listOf("patata", "mongeta", "carbassó")
    }

    renderSeparator()

    ComponentListExample {}

    renderSeparator()

    GenericComponentExample {}

    renderSeparator()

    AnimationByCodeExample()
}

// This is one way to reuse code, however be aware that hooks (useState, useEffect, etc.) MUST NOT
// be used when using this pattern
// The developers of Kotlin Wrappers do not approve of this pattern, but I find it useful and more
// lightweight than creating a component when no hooks are needed
fun ChildrenBuilder.renderSeparator() {
    div {
        css {
            minHeight = 16.px
        }
    }
}