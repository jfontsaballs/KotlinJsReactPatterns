import csstype.Border
import csstype.LineStyle
import csstype.px
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.span

external interface BasicListExampleProps : Props {
	var myItems: List<String>
}

/**
 * This is a component that shows each item in the list surrounded by a border
 */
val BasicListExample = FC<BasicListExampleProps> { props ->
    for (item in props.myItems) {
        span {
            css {
                padding = 4.px
                border = Border(1.px, LineStyle.solid)
            }
            +item
        }
    }
}

