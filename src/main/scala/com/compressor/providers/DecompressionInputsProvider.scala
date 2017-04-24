package com.compressor.providers

import com.compressor.models.DecompressionInputs

trait DecompressionInputsProvider {

  def getDecompressionInputs: DecompressionInputs
}
