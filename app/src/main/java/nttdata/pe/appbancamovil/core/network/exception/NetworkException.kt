package nttdata.pe.appbancamovil.core.network.exception

sealed class NetworkException : Exception() {
    object NotFoundUser : NetworkException()
    object NotFoundAccount : NetworkException()
    object NotFoundUpdateAccount : NetworkException()
}