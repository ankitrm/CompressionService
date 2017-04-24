package com.compressor.app

import com.compressor.di.ServiceInjector
import com.google.inject.Guice

object StartService {

  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new ServiceInjector)
    val zipperApplication: CompressionApplication = injector.getInstance(classOf[CompressionApplication])
    zipperApplication.startApplication
  }
}
