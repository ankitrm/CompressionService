package com.compressor.utils

import scala.collection.parallel.ForkJoinTaskSupport

object Helpers {

  def getParallelTaskSupport: ForkJoinTaskSupport = {
    val cores = Runtime.getRuntime.availableProcessors
    new ForkJoinTaskSupport(new scala.concurrent.forkjoin.ForkJoinPool(cores))
  }
}
