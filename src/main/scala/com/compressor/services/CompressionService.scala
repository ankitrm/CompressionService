package com.compressor.services

import com.compressor.models.{CompressionInputs, FileSize}

trait CompressionService {

  def compressDirectoriesParallely(parallellyCompressionInputs: CompressionInputs): Unit

  def compressDirectoriesSequentially(sequentiallyCompressionInputs: CompressionInputs): Unit

}
