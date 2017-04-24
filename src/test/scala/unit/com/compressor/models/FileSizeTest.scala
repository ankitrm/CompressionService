package unit.com.compressor.models

import com.compressor.models.{EStorageUnitType, FileSize}
import org.scalatest.{Matchers, WordSpec}

class FileSizeTest extends WordSpec with Matchers {
  "FileSize" should {
    "throw an[IllegalArgumentException]" when {
      "size of zip file is 0" in {
        an[IllegalArgumentException] shouldBe thrownBy {
          FileSize(BigDecimal.apply(0), EStorageUnitType.MB)
        }
      }
    }
  }

  "FileSize" should {
    "not throw an[IllegalArgumentException]" when {
      "size of zip file > 0" in {
          FileSize(BigDecimal.apply(1), EStorageUnitType.MB)
      }
    }
  }
}
