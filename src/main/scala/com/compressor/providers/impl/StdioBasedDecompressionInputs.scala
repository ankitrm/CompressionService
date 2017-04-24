package com.compressor.providers.impl

import com.compressor.models.DecompressionInputs
import com.compressor.providers.DecompressionInputsProvider

class StdioBasedDecompressionInputs extends DecompressionInputsProvider {
  override def getDecompressionInputs: DecompressionInputs = {
    println("Enter path for input file/folder: ")
    val inputFolder = scala.io.StdIn.readLine
    println("Enter path for output folder: ")
    val outputFolder = scala.io.StdIn.readLine
    DecompressionInputs(inputFolder, outputFolder)
  }
}
