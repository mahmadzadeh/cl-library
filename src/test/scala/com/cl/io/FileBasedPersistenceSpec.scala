package com.cl.io

import com.cl.FileBasedTest
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source
import scala.util.parsing.json.JSON

class FileBasedPersistenceSpec extends FlatSpec with Matchers with FileBasedTest {

    behavior of "file based data persistence"

    it should "be able to create an instance with given file" in {
        FileBasedPersistence( getFile( "text.json" ) )
    }

    it should "read fucntion should return JSON content of the input file" in {

        print(JSON.parseFull(Source.fromFile(getFile("sampleInput.xml"), "UTF-8").mkString))

        val res = FileBasedPersistence( getFile("sampleInput.xml") ).read()
    }

}
