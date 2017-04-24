package com.compressor.services

import com.compressor.models.DecompressionInputs

trait DecompressionService {

  def parallelyDecompressDirectory(decompressionInputs: DecompressionInputs): Unit

  def sequentialDecompressDirectory(decompressionInputs: DecompressionInputs): Unit

}
