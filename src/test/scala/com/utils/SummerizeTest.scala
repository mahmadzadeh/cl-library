package com.utils

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite}

@RunWith(classOf[JUnitRunner])
class SummerizeTest extends FunSuite with BeforeAndAfter {

    test("given empty string then summerize returns empty string") {
        val str = ""

        assertResult("") {
            Summeriser.summerise(str)
        }
    }

    test("given string with single space then summerize returns it") {
        val str = " "

        assertResult(" ") {
            Summeriser.summerise(str)
        }
    }

    test("given string with single letter then summerize returns it") {
        val str = "A"

        assertResult("A") {
            Summeriser.summerise(str)
        }
    }

    test("given string with length < max Length then summerize returns it") {
        val str = "A" * (Summeriser.MAX_LENGTH -1)
        val expected = "A" * (Summeriser.MAX_LENGTH -1)

        val actual: String = Summeriser.summerise(str)

        assertResult(expected) { actual  }
        assertResult(str.size) { actual.size  }
    }

    test("given string with length == max Length then summerize returns it") {
        val str = "A" * (Summeriser.MAX_LENGTH)
        val expected = "A" * (Summeriser.MAX_LENGTH)

        val actual: String = Summeriser.summerise(str)
        assertResult(expected) { actual  }
        assertResult(Summeriser.MAX_LENGTH) { actual.size  }
    }

    test("given string with length > max Length when string ends in space then summerize returns string with appropriate size") {
        val str = ("A" * (Summeriser.MAX_LENGTH -1 )) + " "
        val expected = str

        val actual: String = Summeriser.summerise(str)
        assertResult(expected) { actual  }
        assertResult(Summeriser.MAX_LENGTH) { actual.size  }
    }


    test("given string with length > max Length when string ends in . then summerize returns string with appropriate size") {
        val str = ("A" * (Summeriser.MAX_LENGTH-1)) + "."
        val expected = str

        val actual: String = Summeriser.summerise(str)
        assertResult(expected) { actual  }
        assertResult(Summeriser.MAX_LENGTH) { actual.size  }
    }

    test("given string with length > max Length when string does not end in space then summerize returns string with trailing ... that adds up to MAX_LENGTH chars") {
        val str = ("A" * (Summeriser.MAX_LENGTH + 1))
        val expected = ("A" * (Summeriser.MAX_LENGTH-3)) + "..."

        val actual: String = Summeriser.summerise(str)
        assertResult(expected) { actual  }
        assertResult(Summeriser.MAX_LENGTH) { actual.size  }
    }
}

object Summeriser {
    val MAX_LENGTH=100

    def summerise(str:String):String =
        if(str.length > MAX_LENGTH) {
            str.substring(0, MAX_LENGTH - 3) + "..."
        } else {
            str
        }
}


