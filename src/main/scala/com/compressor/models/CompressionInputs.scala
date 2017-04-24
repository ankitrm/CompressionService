package com.compressor.models

case class CompressionInputs(inputDirs: Seq[String],
                             outputDir: String,
                             maxFileSize: FileSize)