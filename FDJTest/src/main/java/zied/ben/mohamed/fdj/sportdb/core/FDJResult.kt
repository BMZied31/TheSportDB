package zied.ben.mohamed.fdj.sportdb.core

/**
 * A sealed class that will wrap the result of api calls. It can succeed with a value of type [T],
 * or fail with an instance of [Throwable].
 *
 * @param T The type of the result data.
 */
sealed class FDJResult<out T> {

    /**
     * A data class representing a successful result with a value of type [T].
     *
     * @property data The data value.
     */
    data class Success<out T>(val data: T) : FDJResult<T>()

    /**
     * A data class representing a failed result with an instance of [Throwable].
     *
     * @property error The error instance.
     */
    data class Failure<T>(val error: Throwable?) : FDJResult<T>()
}
