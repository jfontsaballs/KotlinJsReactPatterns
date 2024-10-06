import emotion.react.css
import react.*
import react.dom.html.ReactHTML.div
import utilities.cssProps
import web.cssom.NamedColor
import web.cssom.px
import web.timers.clearInterval
import web.timers.setInterval
import kotlin.time.Duration.Companion.seconds

val AnimationByCodeExample = FC<Props> {
    div {
        css {
            // A properties instance can be merged into another with the unary plus operator
            +myMargin
        }
        val (blink, setBlink) = useState(false)
        useEffectOnceWithCleanup {
            val intervalId = setInterval(1.seconds) { setBlink { !it } }
            onCleanup { clearInterval(intervalId) }
        }
        // A properties instance can also be assigned to an HTML element's style
        // Use style to set formatting when it changes rapidly, otherwise it's better to use css
        style = cssProps {
            color = if (blink) NamedColor.red else NamedColor.black
        }
        +"My animated text"
    }
}

val myMargin = cssProps {
    margin = 12.px
}