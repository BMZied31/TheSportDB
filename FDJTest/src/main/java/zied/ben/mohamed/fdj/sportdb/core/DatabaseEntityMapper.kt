package zied.ben.mohamed.fdj.sportdb.core

/**
 * An interface for data classes that can map objects to database entities of type [T].
 *
 * @param T The type of the database entity.
 */
interface DatabaseEntityMapper<T : Any> {
    /**
     * Maps an object to a database entity of type [T].
     *
     * @return The database entity.
     */
    fun mapToDbEntity(): T
}
