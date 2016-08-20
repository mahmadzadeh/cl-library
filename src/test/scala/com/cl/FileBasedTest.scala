package com.cl

import java.io.File

import scala.io.Source

trait FileBasedTest {

    def readFile(fileName: String): String = {

        val source = Source.fromFile(new File("src/test/resources/" + fileName), "UTF-8")
        val lines = source.mkString

        source.close()

        lines
    }

    def getFile(fileName: String): File = new File("src/test/resources/" + fileName)

    def getTempFileForTesting(fileName: String, extension: String = ".xml"): File = {
        val file = File.createTempFile(fileName, extension)
        assert(file.exists())
        file
    }
}
