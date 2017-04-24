package com.compressor.di

import com.compressor.providers.{CompressionInputsProvider, DecompressionInputsProvider, RandomNumberProvider}
import com.compressor.providers.impl.{ScalaBasedRandomNumberProvider, StdioBasedCompressionInputs, StdioBasedDecompressionInputs}
import com.compressor.services.impl._
import com.compressor.services.{CompressionService, DecompressionService}
import com.google.inject.AbstractModule

class ServiceInjector extends AbstractModule {

  private def bindServices() = {
    bind(classOf[CompressionService]).to(classOf[ZipBasedCompressor])
    bind(classOf[DecompressionService]).to(classOf[ZipBasedDecompressor])
  }

  private def bindProviders() = {
    bind(classOf[CompressionInputsProvider]).to(classOf[StdioBasedCompressionInputs])
    bind(classOf[DecompressionInputsProvider]).to(classOf[StdioBasedDecompressionInputs])
    bind(classOf[RandomNumberProvider]).to(classOf[ScalaBasedRandomNumberProvider])
  }

  override def configure(): Unit = {
    bindProviders()
    bindServices()
  }
}
