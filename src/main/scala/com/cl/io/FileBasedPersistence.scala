package com.cl.io

import java.io.File

import com.cl.result.RssItem

import scala.util.{Success, Try}

object FileBasedPersistence {
    def apply(inputFile: java.io.File = new File("file.json")) =
        new FileBasedPersistence(inputFile)
}

class FileBasedPersistence(inputFile: File) extends Persistence {

    def readPreviousRunData() = ???

    override def read(): Try[Seq[RssItem]] = Success(Nil)//Converter.convertJSOnToRssItem(xml.XML.loadFile(inputFile))

    override def write(rssItems: Seq[RssItem]): Unit = ???
}
