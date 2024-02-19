package annotation

import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
annotation class IgnoreGlobalErrorHandling (
    val ignoreStrategy: KClass<out IgnoreErrorHandlingStrategy> = AllErrorsStrategy::class,
    val ignoreStatusCodes: IntArray = []
)

abstract class IgnoreErrorHandlingStrategy

data object ClientErrorsStrategy : IgnoreErrorHandlingStrategy()
data object ServerErrorsStrategy : IgnoreErrorHandlingStrategy()
data object AllErrorsStrategy : IgnoreErrorHandlingStrategy()
data object StatusCodeStrategy : IgnoreErrorHandlingStrategy()