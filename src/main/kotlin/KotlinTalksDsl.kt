import java.time.LocalTime
import java.time.LocalTime.now

data class KotlinMeetup(val title: String, val talks: List<Talk>)
data class Talk(val title: String, var author: String = "", var startTime: LocalTime = now(), var endTime: LocalTime = now())

fun main() {
    withoutDsl()
    withDsl()
}

fun withoutDsl() {
    val kotlinTalks = KotlinMeetup("Kotlin Talks", listOf(
            Talk("Kotlin, a language to rule DSLs",
                    "Tiberiu Tofan",
                    LocalTime.of(18, 30),
                    LocalTime.of(19, 15)),
            Talk("Kotlin for backend",
                    "Cosmin Stefan",
                    LocalTime.of(19, 20),
                    LocalTime.of(20, 5)),
            Talk("Effects in the wild, an introduction to functional programming",
                    "Gabriel Bornea",
                    LocalTime.of(20, 10),
                    LocalTime.of(20, 55))
    ))

    println(kotlinTalks)
}

fun withDsl() {
    val kotlinTalks = kotlinMeetup("Kotlin Talks") {
        +"Kotlin, a language to rule DSLs" by "Tiberiu Tofan" from 18.30 to 19.15
        +"Kotlin for backend" by "Cosmin Stefan" from 19.20 to 20.05
        +"Effects in the wild, an introduction to functional programming" by "Gabriel Bornea" from 20.10 to 20.55
    }

    println(kotlinTalks)
}

class KotlinMeetupBuilder(private var title: String) {
    private val talks = mutableListOf<Talk>()
    fun talks(vararg talks: Talk) = this.talks.addAll(talks)
    fun build() = KotlinMeetup(title, talks)
    operator fun Talk.unaryPlus(): Talk = apply { talks.add(this) }
    operator fun String.unaryPlus(): Talk = Talk(this).apply { talks.add(this) }
}

fun talk(title: String, init: Talk.() -> Unit): Talk = Talk(title).apply(init)
fun kotlinMeetup(title: String, init: KotlinMeetupBuilder.() -> Unit): KotlinMeetup = KotlinMeetupBuilder(title).apply(init).build()

infix fun String.by(author: String): Talk = Talk(this, author)

infix fun Talk.from(startTime: Double): Talk = apply { this.startTime = startTime.hours }
infix fun Talk.to(endTime: Double): Talk = apply { this.endTime = endTime.hours }
infix fun Talk.by(author: String): Talk = apply { this.author = author }

val Double.hours: LocalTime
    get() {
        val timeAsString = toString()
        val indexOfDecimal = timeAsString.indexOf(".")
        return LocalTime.of(timeAsString.substring(0, indexOfDecimal).toInt(),
                timeAsString.substring(indexOfDecimal + 1).padEnd(2, '0').toInt())
    }
