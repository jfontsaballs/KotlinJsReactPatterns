import csstype.Border
import csstype.LineStyle
import csstype.NamedColor
import csstype.px
import emotion.react.css
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span

/**
 * This is a sample of using a component to separate the layout logic for other
 * components
 */
val ComponentListExample = VFC {
    ComponentList {
        listItems = listOf(
            // In this case we don't have the help of the ChildrenBuilder, so we need to
            // manually create the components by calling the create function.
            StringListItem.create { content = "AAA" },
            IntListItem.create { content = 2 },
            StringListItem.create { content = "CCC" },
        )
    }
    ComponentList2 {
        listItems = listOf(
            // This way, we do have a ChildrenBuilder inside each list item, however be aware that it is
            // the ChildrenBuilder of the span that ComponentList2 creates for each item
            { StringListItem { content = "VVV" } },
            { IntListItem { content = 3 } },
            { StringListItem { content = "BBB" } },
        )
    }
}

external interface ComponentListProps : Props {
    var listItems: Collection<ReactNode>
}

/**
 * This is a component that takes a list of React components and shows it
 * Not all the components need to be of the same type
 */
private val ComponentList = FC<ComponentListProps> { props ->
    div {
        for (item in props.listItems) {
            span {
                css { border = Border(1.px, LineStyle.solid) }
                +item
            }
        }
    }
}

external interface StringListItemProps : Props {
    var content: String
}

private val StringListItem = FC<StringListItemProps> { props ->
    span {
        css { color = NamedColor.green }
        +props.content
    }
}

external interface IntListItemProps : Props {
    var content: Int
}

private val IntListItem = FC<IntListItemProps> { props ->
    span {
        css { color = NamedColor.blue }
        +props.content.toString()
    }
}

// Instead of passing components, we might pass functions
// However, this does not allow for hooks to be used inside these functions and
// is discouraged by the Kotlin Wrappers development team
external interface ComponentList2Props : Props {
    var listItems: Collection<ChildrenBuilder.() -> Unit>
}

/**
 * This is a component that takes a list of React components and shows it
 * Not all the components need to be of the same type
 */
private val ComponentList2 = FC<ComponentList2Props> { props ->
    div {
        for (item in props.listItems) {
            span {
                css { border = Border(1.px, LineStyle.solid) }
                item()
            }
        }
    }
}