/**
 * Created on 2019-02-10.
 */
class CircularList<T>(private val list: List<T>) : List<T> by list {

    override fun get(index: Int): T =
        list[index.safely()]

    // Other overrides removed for brevity.
    /**
     * Get a [kotlin.collections.ListIterator] starting at the specified [index]
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.CircularList.listIterator
     */
    override fun listIterator(index: Int): ListIterator<T> =
        list.listIterator(index.safely())

    /**
     * Get a List bound by [fromIndex] inclusive to [toIndex] exclusive
     *
     * If [fromIndex] or [toIndex] is negative they are interpreted as an offset from the end of
     * the list. If the [fromIndex] or [toIndex] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.CircularList.subList
     */
    override fun subList(fromIndex: Int, toIndex: Int): List<T> =
        list.subList(fromIndex.safely(), toIndex.safely())

    /**
     * Returns a String representation of the object.
     */
    override fun toString(): String =
        list.toString()

    private fun Int.safely(): Int =
        if (this < 0) (this % size + size) % size
        else this % size

    fun next(t: T): T {
        val index = this.indexOf(t)
        return this[index + 1]
    }

    fun prev(t: T): T {
        val index = this.indexOf(t)
        return this[index - 1]
    }

}

fun <T> List<T>.circular(): CircularList<T> = CircularList(this)
