package integration.com.compressor.services.impl

import java.io.File
import java.nio.file.{Files, Paths}

import com.compressor.models.{CompressionInputs, EStorageUnitType, FileSize}
import com.compressor.providers.RandomNumberProvider
import com.compressor.services.impl.ZipBasedCompressor
import com.google.common.io.Resources
import org.mockito.Matchers.any
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

class ZipBasedCompressorTest extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  private val mockRandomNumberProvider = mock[RandomNumberProvider]
  private val zipBasedCompressor = new ZipBasedCompressor(mockRandomNumberProvider)
  private val fileSeparator = File.separator
  private val resourcePath = Resources.getResource("").getPath
  private val pathZip1 = resourcePath + "TestZip1"
  private val pathZip2 = resourcePath + "TestZip2"
  private val pathZipped1 = resourcePath + "TestZipped1"
  private val pathZipped2 = resourcePath + "TestZipped2"
  private val testZippedFileNamePath1 = Paths.get(pathZipped1 + fileSeparator + "TestZip112.zip")
  private val testZippedFileNamePath2 = Paths.get(pathZipped2 + fileSeparator + "TestZip212.zip")
  private val folderPathZipped1 = Paths.get(pathZipped1)
  private val folderPathZipped2 = Paths.get(pathZipped2)

  before {
    when(mockRandomNumberProvider.getRandomInt(any())).thenReturn(12)
    Files.createDirectory(folderPathZipped1)
    Files.createDirectory(folderPathZipped2)
  }
  after {
    reset(mockRandomNumberProvider)
    Files.deleteIfExists(testZippedFileNamePath1)
    Files.deleteIfExists(testZippedFileNamePath2)
    Files.delete(folderPathZipped1)
    Files.delete(folderPathZipped2)
  }

  "compressDirectoriesSequentially" should {
    "be able to compress a directory" when {
      "valid set of inputs are passed" in {
        val testCompressionInputs = CompressionInputs(Seq(pathZip1), pathZipped1, FileSize(BigDecimal.apply(1), EStorageUnitType.MB))

        zipBasedCompressor.compressDirectoriesSequentially(testCompressionInputs)

        verify(mockRandomNumberProvider).getRandomInt(100)
        Files.exists(testZippedFileNamePath1) shouldBe true
      }
    }
  }

  "compressDirectoriesParallely" should {
    "be able to compress a directory" when {
      "valid set of inputs are passed" in {
        val testCompressionInputs = CompressionInputs(Seq(pathZip2), pathZipped2, FileSize(BigDecimal.apply(1), EStorageUnitType.MB))

        zipBasedCompressor.compressDirectoriesParallely(testCompressionInputs)

        verify(mockRandomNumberProvider).getRandomInt(100)
        Files.exists(testZippedFileNamePath2) shouldBe true
      }
    }
  }
}
