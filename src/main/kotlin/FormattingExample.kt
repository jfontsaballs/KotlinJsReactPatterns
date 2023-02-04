import csstype.Length
import csstype.PropertiesBuilder
import csstype.px
import emotion.css.cx
import react.FC
import react.Props
import emotion.react.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useState
import web.html.InputType

external interface FormattingExampleProps : Props {
	var name: String
}

/**
 * This is a basic component with properties.
 * It also shows how to use inline css
 * BE CAREFUL because there are different implementations of css properties in different namespaces
 * We have to use the ones in csstype namespace, as this are the ones that emotion expects
 */
val FormattingExample = FC<FormattingExampleProps> { props ->
	var name by useState(props.name)
	div {
		// We might combine css classes declared in css files with inline css properties
		// Be careful that function ClassName exists in two different namespaces, and there
		// also exists an interface called ClassName
		// - csstype.ClassName returns an object implementing the ClassName interface from a String,
		//   it is used to refer to classes declared in a conventional .css file
		// - emotion.css.ClassName returns an object implementing the ClassName interface from a block
		//   of code defining some CSS
		// Both functions return an instance of class ClassName, which can be used in the css extension function
		// that emotion provides
		// Be careful to use the correct import: emotion.react.css
		css(csstype.ClassName("myBackgroundColor"), csstype.ClassName("myForegroundColor")) {
			padding = 5.px
		}

		// Or they can be assigned to the className property, using the cx function to combine them
		className = cx(csstype.ClassName("myBackgroundColor"), csstype.ClassName("myForegroundColor"))

		// Or concatenating them
		className = csstype.ClassName("myBackgroundColor, myForegroundColor")

		// In Javascript, we would return content (JSX) from a function component for it to be rendered
		// In Kotlin Wrappers they allow for content to be added throughout the function. This is accomplished
		// via the ChildrenBuilder context that every functional component is provided
		// - HTML components and React components are added to the parent component when they are invoked; the
		//   ChildrenBuilder interface contains an invoke operator on
		+"Hello, $name"
	}
	input {
		// We might combine css classes declared in code with inline css properties
		css(myInput) {
			fontSize = 14.px
			applyLeftRightMargin(3.px)
		}
		type = InputType.text
		value = name
		onChange = { event ->
			name = event.target.value
		}
	}
}

// We might create a CSS class and use it elsewhere
val myInput = emotion.css.ClassName {
	// When defining CSS in code, dashes disappear and camelCase is used
	marginTop = 5.px // that would be margin-top in a normal css file
	marginBottom = 5.px
}

// We might also define extension functions on PropertiesBuilder
// I don't know if the developers of Kotlin Wrappers would approve of this pattern,
// but I've found it useful in certain situations
fun PropertiesBuilder.applyLeftRightMargin(marginValue: Length) {
	marginLeft = marginValue
	marginRight = marginValue
}