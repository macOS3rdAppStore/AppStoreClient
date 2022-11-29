import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class TestCoroutine : KoinComponent {
    val aa: String by inject()

    override fun toString(): String {
        println(aa)
        return super.toString()
    }
}