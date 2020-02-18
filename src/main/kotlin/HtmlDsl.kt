import kotlinx.html.ATarget
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.meta
import kotlinx.html.stream.appendHTML
import kotlinx.html.title

fun main() {
    System.out.appendHTML().html {
        head {
            meta {
                title = "Generated in Kotlin"
            }
        }
        body {
            div {
                a(href = "https://kotlinlang.org") {
                    target = ATarget.blank
                    +"Main site"
                }
            }
        }
    }
}
