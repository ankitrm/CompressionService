package com.compressor.providers

import com.compressor.models.CompressionInputs

trait CompressionInputsProvider {

  def getCompressionInputsForInputDirectory: CompressionInputs

  def getCompressionInputsForInputDirectories: CompressionInputs
}
