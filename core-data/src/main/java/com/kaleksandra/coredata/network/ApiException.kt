package com.kaleksandra.coredata.network

sealed class ApiException(message: String? = null, cause: Throwable? = null) :
    Exception(message, cause) {

    companion object {
        fun Int.toApiException(): ApiException {
            return when (this) {
                404 -> ResourceNotFoundException()
                400 -> BadRequestException()
                401 -> UnauthorizedException()
                403 -> ForbiddenException()
                409 -> ConflictException()
                500 -> InternalServerErrorException()
                else -> UnknownException()
            }
        }
    }

    // Exception for resource not found (404)
    class ResourceNotFoundException(message: String? = "Resource not found") : ApiException(message)

    // Exception for invalid request (400)
    class BadRequestException(message: String? = "Bad request") : ApiException(message)

    // Exception for unauthorized access (401)
    class UnauthorizedException(message: String? = "Unauthorized") : ApiException(message)

    // Exception for forbidden access (403)
    class ForbiddenException(message: String? = "Forbidden") : ApiException(message)

    // Exception for conflict in resource state (409)
    class ConflictException(message: String? = "Conflict") : ApiException(message)

    // Exception for internal server error (500)
    class InternalServerErrorException(message: String? = "Internal server error") :
        ApiException(message)

    // Exception for others
    class UnknownException(message: String? = "Unknown exception") : ApiException(message)
}