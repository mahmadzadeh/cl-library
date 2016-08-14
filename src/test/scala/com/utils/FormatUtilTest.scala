package com.utils

import org.scalatest.{Matchers, FlatSpec}


class FormatUtilSpec extends FlatSpec with Matchers {

    behavior of "format util"

    it should "format a double to zero decimal point" in {
        assertResult("2001") {
            FormatUtil.fmtDouble(2001.11)
        }

        assertResult("2001") {
            FormatUtil.fmtDouble(2001)
        }
    }

}
