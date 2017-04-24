package com.compressor.models

import com.compressor.models.EStorageUnitType.EStorageUnitType

case class FileSize(size: BigDecimal,
                    unit: EStorageUnitType) {
  require(size > BigDecimal.apply(0))
}
