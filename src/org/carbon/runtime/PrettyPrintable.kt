package org.carbon.runtime

/**
 * Mixin to allow objects to be pretty printable.
 *
 * Only override {@link #getShortString()} and {@link #getBodyString(int)}
 *
 * When using, only call {@link #getShortString} and {@link #getFullString}
 *
 * Strings returned by this are for debugging purposes and should not be relied upon.
 *
 * TODO document how to override and implement. For now, see code examples
 *
 * @author Ethan
 */
interface PrettyPrintable {
    fun getShortString(): String

    /**
     * Do not override, just use.
     *
     * Strings returned by this are for debugging purposes and should not be relied upon.
     */
    fun getBodyString(): String {
        return getBodyString(0)
    }

    /**
     * Override this
     */
    fun getBodyString(level: Int): String {
        return ""
    }

    /**
     * Use this method, do not override.
     *
     * Strings returned by this are for debugging purposes and should not be relied upon.
     */
    fun getFullString(): String {
        return getFullString(0)
    }

    /**
     * Do not override this method. Use is required only when implementing the interface.
     */
    fun getFullString(level: Int): String {
        return indent(level) + getShortString() + bodyWithReturn(level + 1, this)
    }
}

fun indent(levels: Int): String = "  ".repeat(levels)

fun indented(level: Int, message: String) = indent(level) + message + "\n"

fun fullString(level: Int, children: Set<PrettyPrintable>): String = fullString(level, HashSet(children))


fun fullString(level: Int, children: List<PrettyPrintable>): String =
        children.joinToString("\n") { p -> p.getFullString(level) }


fun fullString(level: Int, children: Map<String, PrettyPrintable>): String {
    return children.entries.joinToString("\n") { e ->
        indent(level) + e.key + " = " + e.value.getShortString() + bodyWithReturn(level, e.value)
    }
}

private fun bodyWithReturn(level: Int, printable: PrettyPrintable): String {
    val bodyString = printable.getBodyString(level)
    return if (bodyString.isEmpty()) "" else "\n" + bodyString
}