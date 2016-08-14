package com.cl

import org.scalatest.{BeforeAndAfter, FunSuite}
import scala.Some
import java.io.File
import org.junit.Before
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.cl.result.AdPage

@RunWith(classOf[JUnitRunner])
class AdPageTest extends FunSuite with BeforeAndAfter {

    test("can crete page instance") {
        assert(new AdPage(Some("<html></html>")) != null)
    }

    test("page ad needs a page to parse") {
        intercept[IllegalArgumentException] {
            new AdPage(None)
        }

    }

    test("parse an invalid html file") {

        val adPage = getAnAdPage("invalidSampleAd.html")

        expect(None) {
            adPage.getTitle()
        }

    }

    test("can get ad title when present in page html") {

        val adPage = getAnAdPage("sampleAd.html")

        assertResult("5th Wheel trailer REDUCED- (camper) - $6500 (surrey)") {
            adPage.getTitle() get
        }
    }

    test("concatenate titles when titles present in page html") {

        val adPage = getAnAdPage("sampleAdWithTwoTitles.html")

        assertResult("5th Wheel trailer REDUCED- (camper) - $6500 (surrey) And Another Title") {
            adPage.getTitle() get
        }
    }

    test("get ad ID") {

        val adPage = getAnAdPage("sampleAd.html")

        assertResult("3973013476") {
            adPage.getAdId()
        }
    }

    test("get posting date ") {

        val adPage = getAnAdPage("sampleAd.html")

        assertResult(Some("1375328092000")) {
            adPage.getAdPostingEpochTime()
        }
    }

    test("get posting date with posting time when html element is renamed") {

        val adPage = getAnAdPage("adWithPostingInfoElementRenamed.html")

        assertResult(None) {
            adPage.getAdPostingEpochTime()
        }
    }

    def getAnAdPage(fileName: String): AdPage = {

        val pageContent = readFile(fileName)

        new AdPage(Some(pageContent))
    }

    import scala.io.Source

    def readFile(fileName: String): String = {

        val source = Source.fromFile(new File("src/test/resources/" + fileName), "UTF-8")
        val lines = source.mkString

        source.close()

        lines


    }

}
