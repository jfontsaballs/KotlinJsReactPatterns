import csstype.*
import emotion.react.css
import react.FC
import react.PropsWithChildren
import react.dom.html.ReactHTML.span

/**
 * We are going to take the BasicListExample and extract the logic for creating the border
 */
val ComponentWithChildrenExample = FC<BasicListExampleProps> { props ->
    for (item in props.myItems) {
        SurroundByBorder {
            // Both spans will be passed as children of SurroundByBorder
            span {
                css { padding = 4.px }
                +item
            }
            span {
                css {
                    width = 2.px
                    height = 5.px
                    margin = 1.px
                    background = NamedColor.red
                }
                +"---"
            }
        }
    }
}

/**
 * Using PropsWithChildren instead of Props, we are signaling that this component
 * may have content (children) passed into it
 */
external interface SurroundByBorderProps : PropsWithChildren {
    var borderWidth: Length?
}

val SurroundByBorder = FC<SurroundByBorderProps> { props ->
    span {
        css { border = Border(props.borderWidth ?: 1.px, LineStyle.solid) }
        // Here we are rendering whatever content is added when this component is called
        +props.children
    }
}