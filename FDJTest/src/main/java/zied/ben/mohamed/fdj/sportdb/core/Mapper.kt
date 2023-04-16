package zied.ben.mohamed.fdj.sportdb.core

/**
 * An interface for data classes that can map objects to domain models of type [T].
 *
 * @param T The type of the domain model.
 */
interface Mapper<T : Any> {
    /**
     * Maps an object to a domain model of type [T].
     *
     * @return The domain model.
     */
    fun mapToDomainModel(): T
}
