package com.compressor.services.impl

import java.io.File

import com.compressor.exceptions.CompressionServiceException
import com.compressor.models.{CompressionInputs, EStorageUnitType, FileSize}
import com.compressor.providers.RandomNumberProvider
import com.compressor.services.CompressionService
import com.compressor.utils.Helpers
import com.google.inject.Inject
import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.util.Zip4jConstants

class ZipBasedCompressor @Inject()(randomNumberProvider: RandomNumberProvider) extends CompressionService {

  private def getMultificationFactor(maxCompressedFileSize: FileSize) = {
    if(maxCompressedFileSize.unit == EStorageUnitType.TB)
      maxCompressedFileSize.size.toLong * 1024 * 1024 * 1024 * 1024
    else if (maxCompressedFileSize.unit == EStorageUnitType.GB)
      maxCompressedFileSize.size.toLong * 1024 * 1024 * 1024
    else if (maxCompressedFileSize.unit == EStorageUnitType.MB)
      maxCompressedFileSize.size.toLong * 1024 * 1024
    else if (maxCompressedFileSize.unit == EStorageUnitType.KB)
      maxCompressedFileSize.size.toLong * 1024
    else if (maxCompressedFileSize.unit == EStorageUnitType.B)
      maxCompressedFileSize.size.toLong
    else
      throw CompressionServiceException(s"Invalid file size for enum $maxCompressedFileSize")
  }

  override def compressDirectoriesParallely(compressionInputs: CompressionInputs): Unit = {

    println("called compressDirectoriesParallely")
    val maxCompressedFileSize = compressionInputs.maxFileSize
    val inputDirectories = compressionInputs.inputDirs
    val outputDirectory = compressionInputs.outputDir
    val parameters = new ZipParameters
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE)
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA)
    val multFactor = getMultificationFactor(maxCompressedFileSize)
    val parInputDirectories = inputDirectories.par
    parInputDirectories.tasksupport = Helpers.getParallelTaskSupport
    parInputDirectories.foreach(inputDirectory =>
      createZipFileFromFolder(inputDirectory, parameters, isSplit = true, multFactor.toLong, outputDirectory)
    )
  }

  override def compressDirectoriesSequentially(compressionInputs: CompressionInputs): Unit = {
    println("called compressDirectoriesSequentially")
    val maxCompressedFileSize = compressionInputs.maxFileSize
    val inputDirectories = compressionInputs.inputDirs
    val outputDirectory = compressionInputs.outputDir
    val parameters = new ZipParameters
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE)
    parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA)
    val multFactor = getMultificationFactor(maxCompressedFileSize)
    inputDirectories.foreach(inputDirectory =>
      createZipFileFromFolder(inputDirectory, parameters, isSplit = true, multFactor, outputDirectory)
    )
  }

  private def getOutputFilename(outputFolder: String, inputDirectory: String) = {
    outputFolder + (File.separator + inputDirectory.split(File.separator).last + randomNumberProvider.getRandomInt(100)) + ".zip"
  }

  private def createZipFileFromFolder(inputDirectory: String, parameters: ZipParameters, isSplit: Boolean, maxSplitSizeInBytes: Long, outputDirectory: String): Unit = {
    val outputFile = getOutputFilename(outputDirectory, inputDirectory)
    val zipFile = new ZipFile(outputFile)
    zipFile.createZipFileFromFolder(inputDirectory, parameters, isSplit, maxSplitSizeInBytes)
  }
}
