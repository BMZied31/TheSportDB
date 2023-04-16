package zied.ben.mohamed.fdj.sportdb.core

/**
 * An interface for use cases in the application.
 *
 * @param Type The type of data returned by the use case.
 */
interface BaseUseCase<out Type> {

    /**
     * Executes the use case.
     *
     * @return The result of executing the use case.
     */
    suspend operator fun invoke(): Type
}
