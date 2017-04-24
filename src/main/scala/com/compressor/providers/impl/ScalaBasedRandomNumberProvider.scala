package com.compressor.providers.impl

import com.compressor.providers.RandomNumberProvider

import scala.util.Random

class ScalaBasedRandomNumberProvider extends RandomNumberProvider{
  override def getRandomInt(limit: Int): Int = {
    Random.nextInt(100)
  }
}
