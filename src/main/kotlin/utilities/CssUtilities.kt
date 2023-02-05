package utilities

import csstype.PropertiesBuilder
import js.core.jso

fun cssProps(block: PropertiesBuilder.() -> Unit) = jso(block)