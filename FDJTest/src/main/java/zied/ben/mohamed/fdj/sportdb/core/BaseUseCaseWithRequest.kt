package zied.ben.mohamed.fdj.sportdb.core

/**
 * An interface for use cases with request in the application.
 *
 * @param Request The type of input data passed to the use case.
 * @param Type The type of data returned by the use case.
 */
interface BaseUseCaseWithRequest<in Request, out Type> {

    /**
     * Executes the use case.
     *
     * @return The result of executing the use case.
     */
    suspend operator fun invoke(request: Request): Type
}
