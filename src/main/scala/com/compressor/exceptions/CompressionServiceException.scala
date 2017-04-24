package com.compressor.exceptions

/**
  * This Exception is thrown when the service fails during compression
  */
case class CompressionServiceException(message: String = "", cause: Throwable = None.orNull)
  extends Exception(message, cause)
