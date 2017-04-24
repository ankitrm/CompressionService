package com.compressor.app

import com.compressor.providers.{CompressionInputsProvider, DecompressionInputsProvider}
import com.compressor.services.{CompressionService, DecompressionService}
import com.google.inject.Inject

class CompressionApplication @Inject()(compressionInputsProvider: CompressionInputsProvider,
                                       compressionService: CompressionService,
                                       decompressionService: DecompressionService,
                                       decompressionInputsProvider: DecompressionInputsProvider
                                      ) {

  def startApplication(): Unit = {
    try {
      println("What operation do you want? \n1. Compress Sequentially \n2. Decompress Sequentially \n3. Compress Parallely \n4. Decompress Parallely \nEnter your choice [1/2/3/4]: ")
      val operationChoice = scala.io.StdIn.readInt
      operationChoice match {
        case 1 =>
          val sequentiallyCompressionInputs = compressionInputsProvider.getCompressionInputsForInputDirectory
          compressionService.compressDirectoriesSequentially(sequentiallyCompressionInputs)

        case 2 =>
          val decompressionInputs = decompressionInputsProvider.getDecompressionInputs
          decompressionService.sequentialDecompressDirectory(decompressionInputs)

        case 3 =>
          val parallellyCompressionInputs = compressionInputsProvider.getCompressionInputsForInputDirectories
          compressionService.compressDirectoriesParallely(parallellyCompressionInputs)

        case 4 =>
          val decompressionInputs = decompressionInputsProvider.getDecompressionInputs
          decompressionService.parallelyDecompressDirectory(decompressionInputs)

        case invalidChoice =>
          throw new IllegalArgumentException(s"$invalidChoice for operation choice")
      }
    } catch {
      case badUserRequest: IllegalArgumentException =>
        println(s"Invalid input: $badUserRequest")
    }
  }
}
