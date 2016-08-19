package com.utils

import org.scalatest.{FlatSpec, Matchers}


class FormatUtilSpec extends FlatSpec with Matchers {

    behavior of "format util fmtDouble "

    it should "format a double to zero decimal point" in {
        assertResult("2001") {
            FormatUtil.fmtDouble(2001.11)
        }

        assertResult("2001") {
            FormatUtil.fmtDouble(2001)
        }
    }
}
