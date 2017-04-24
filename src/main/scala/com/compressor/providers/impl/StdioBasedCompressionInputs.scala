package com.compressor.providers.impl

import com.compressor.exceptions.CompressionServiceException
import com.compressor.models.{CompressionInputs, EStorageUnitType, FileSize}
import com.compressor.providers.CompressionInputsProvider

class StdioBasedCompressionInputs extends CompressionInputsProvider {
  override def getCompressionInputsForInputDirectory: CompressionInputs = {
    println("Enter path for input folder: ")
    val inputFolder = scala.io.StdIn.readLine
    println("Enter path for output folder: ")
    val outputFolder = scala.io.StdIn.readLine
    println("Enter maximum size compressed file in MB, e.g 10: ")
    val sizeCompressedFile = scala.io.StdIn.readDouble
    CompressionInputs(Seq(inputFolder), outputFolder, FileSize(BigDecimal.apply(sizeCompressedFile), EStorageUnitType.MB))
  }

  override def getCompressionInputsForInputDirectories: CompressionInputs = {
    println("Enter paths for input folders followed by a comma (,): ")
    val inputFolders = scala.io.StdIn.readLine
    val listOfInputFolders = inputFolders.split(",").map(_.trim)
    if(listOfInputFolders.isEmpty)
      CompressionServiceException(s"Invalid list of inputs from input folders: $listOfInputFolders")
    println("Enter path for output folder: ")
    val outputFolder = scala.io.StdIn.readLine
    println("Enter maximum size compressed file in MB, e.g 10: ")
    val sizeCompressedFile = scala.io.StdIn.readDouble
    CompressionInputs(listOfInputFolders, outputFolder, FileSize(BigDecimal.apply(sizeCompressedFile), EStorageUnitType.MB))
  }
}
