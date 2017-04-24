package com.compressor.providers

trait RandomNumberProvider {

  def getRandomInt(limit: Int): Int
}
