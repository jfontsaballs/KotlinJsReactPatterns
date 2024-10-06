package utilities

import csstype.PropertiesBuilder
import js.objects.jso

fun cssProps(block: PropertiesBuilder.() -> Unit) = jso(block)