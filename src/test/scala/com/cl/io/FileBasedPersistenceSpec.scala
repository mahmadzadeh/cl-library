package com.cl.io

import com.cl.FileBasedTest
import com.cl.io.persistence.FileBasedPersistence
import org.scalatest.{FlatSpec, Matchers}

class FileBasedPersistenceSpec extends FlatSpec with Matchers with FileBasedTest {

    behavior of "file based data persistence"

    it should "be able to create an instance with given file" in {
        FileBasedPersistence(getFile("text.json"))
    }

    behavior of "file based persistence's read function"

    it should "read function returns Try.Failure when input file does not exist" in {
        val tryRes = FileBasedPersistence(getFile("fileThatIsNotThere.xml")).read()

        assert(tryRes.isFailure)
    }

    it should "read function should RSS results based on the content of the input file" in {
        val tryRes = FileBasedPersistence(getFile("ads.xml")).read()
        assert(tryRes.isSuccess)

        val res = tryRes.get

        val expectedNumberOfRssItems = 3

        assertResult(expectedNumberOfRssItems) {
            res.size
        }

        assertResult(Set(123456, 123457, 123458)) {
            res.ids
        }
    }

    behavior of "file based persistence's write function"

    it should "write function should write RSSItems to a given file" in {

        val tryRssItems = FileBasedPersistence(getFile("ads.xml")).read()
        assert(tryRssItems.isSuccess)

        val rssItems = tryRssItems.get

        val outputFile = getTempFileForTesting("sampleInput")

        FileBasedPersistence(outputFile).write(rssItems)

        assert(outputFile.exists())

        val readDataFromNewlyCreatedFile = FileBasedPersistence(outputFile).read()

        assertResult(rssItems.ids) {
            rssItems.ids
        }

    }

}
