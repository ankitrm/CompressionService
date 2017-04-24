package integration.com.compressor.services.impl

import java.io.File
import java.nio.file.{Files, Paths}

import com.compressor.exceptions.CompressionServiceException
import com.compressor.models.DecompressionInputs
import com.compressor.services.impl.ZipBasedDecompressor
import com.google.common.io.Resources
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

import scala.io.Source

class ZipBasedDecompressorTest extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter{

  private val zipBasedDecompressor = new ZipBasedDecompressor
  private val fileSeparator = File.separator
  private val resourcePath = Resources.getResource("").getPath
  private val pathUnzip1 = resourcePath + "TestUnzip1"
  private val pathUnzip2 = resourcePath + "TestUnzip2"
  private val pathOutputUnzipped1 = resourcePath + "TestUnzipped1"
  private val pathOutputUnzipped2 = resourcePath + "TestUnzipped2"
  private val resultZipFolder1 = pathOutputUnzipped1 + fileSeparator + "TestZip1"
  private val resultZipFolder2 = pathOutputUnzipped2 + fileSeparator + "TestZip2"
  private val resultFile1 = resultZipFolder1 + fileSeparator + "testZipFile1.txt"
  private val resultFile2 = resultZipFolder2 + fileSeparator + "testZipFile2.txt"

  private val resultUnzippedFolder1 = Paths.get(resultZipFolder1)
  private val resultUnzippedFolder2 = Paths.get(resultZipFolder2)
  private val resultUnzippedFile1 = Paths.get(resultFile1)
  private val resultUnzippedFile2 = Paths.get(resultFile2)
  private val folderPathUnzipped1 = Paths.get(pathOutputUnzipped1)
  private val folderPathUnzipped2 = Paths.get(pathOutputUnzipped2)

  before {
    Files.createDirectory(folderPathUnzipped1)
    Files.createDirectory(folderPathUnzipped2)
  }
  after {
    Files.deleteIfExists(resultUnzippedFile1)
    Files.deleteIfExists(resultUnzippedFile2)
    Files.deleteIfExists(resultUnzippedFolder1)
    Files.deleteIfExists(resultUnzippedFolder2)
    Files.delete(folderPathUnzipped1)
    Files.delete(folderPathUnzipped2)
  }

  "sequentialDecompressDirectory" should {
    "be able to unzip test folder" when {
      "valid set of inputs are passed" in {
        val testDecompressionInputs = DecompressionInputs(pathUnzip1, pathOutputUnzipped1)

        zipBasedDecompressor.sequentialDecompressDirectory(testDecompressionInputs)

        Files.exists(resultUnzippedFile1) shouldBe true
        val testUnzippedFile1 = Source.fromFile(resultFile1).mkString
        testUnzippedFile1 shouldEqual  "hello world, this is file 1"
      }
    }

    "throw a CompressionServiceException" when {
      "a directory with no zip files is passed" in {
        val testDecompressionInputs = DecompressionInputs(pathOutputUnzipped1, pathUnzip1)

        an[CompressionServiceException] shouldBe thrownBy(zipBasedDecompressor.sequentialDecompressDirectory(testDecompressionInputs))
      }
    }
  }

  "parallelyDecompressDirectory" should {
    "be able to unzip test folder" when {
      "valid set of inputs are passed" in {
        val testDecompressionInputs = DecompressionInputs(pathUnzip2, pathOutputUnzipped2)

        zipBasedDecompressor.parallelyDecompressDirectory(testDecompressionInputs)

        Files.exists(resultUnzippedFile2) shouldBe true
        val testUnzippedFile2 = Source.fromFile(resultFile2).mkString
        testUnzippedFile2 shouldEqual  "hello world, this is file 2"
      }
    }

    "throw a CompressionServiceException" when {
      "a directory with no zip files is passed" in {
        val testDecompressionInputs = DecompressionInputs(pathOutputUnzipped2, pathUnzip2)

        an[CompressionServiceException] shouldBe thrownBy(zipBasedDecompressor.sequentialDecompressDirectory(testDecompressionInputs))
      }
    }
  }
}
