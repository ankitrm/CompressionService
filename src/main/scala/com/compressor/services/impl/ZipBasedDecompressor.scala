package com.compressor.services.impl

import java.io.File

import com.compressor.exceptions.CompressionServiceException
import com.compressor.models.DecompressionInputs
import com.compressor.services.DecompressionService
import com.compressor.utils.Helpers
import net.lingala.zip4j.core.ZipFile

class ZipBasedDecompressor extends DecompressionService {

  override def parallelyDecompressDirectory(decompressionInputs: DecompressionInputs): Unit = {
    println("called parallelyDecompressDirectory")
    val inputDirectory = decompressionInputs.inputDir
    val outputDirectory = decompressionInputs.outputDir
    val filesToBeUnzipped = getZipFiles(inputDirectory).par
    filesToBeUnzipped.tasksupport = Helpers.getParallelTaskSupport
    filesToBeUnzipped.foreach(fileToBeUnzipped => {
      val zipFile = new ZipFile(fileToBeUnzipped)
      zipFile.extractAll(outputDirectory)
    })
  }

  override def sequentialDecompressDirectory(decompressionInputs: DecompressionInputs): Unit = {
    println("called decompressDirectory")
    val inputDirectory = decompressionInputs.inputDir
    val outputDirectory = decompressionInputs.outputDir
    val filesToBeUnzipped = getZipFiles(inputDirectory)
    filesToBeUnzipped.foreach(fileToBeUnzipped => {
      val zipFile = new ZipFile(fileToBeUnzipped)
      zipFile.extractAll(outputDirectory)
    })
  }

  private def getZipFiles(dir: String) = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      val listOfValidZipFiles = d.listFiles.filter(file => file.isFile && file.getName.endsWith(".zip")).toSeq
      if (listOfValidZipFiles.isEmpty)
        throw CompressionServiceException(s"No files to zip for directory $dir")
      listOfValidZipFiles
    } else {
      throw CompressionServiceException(s"Invalid path for directory $dir")
    }
  }
}
