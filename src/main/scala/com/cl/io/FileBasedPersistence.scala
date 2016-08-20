package com.cl.io

import java.io.File

import com.cl.io.Converter.convertRssItemsFromXML
import com.cl.result.RssItems

object FileBasedPersistence {
    val DATA_FILE_NAME = "data.xml"

    def apply(inputFile: java.io.File = new File(DATA_FILE_NAME)) =
        new FileBasedPersistence(inputFile)
}

class FileBasedPersistence(inputFile: File) extends Persistence {

    override def read(): RssItems = convertRssItemsFromXML(xml.XML.loadFile(inputFile))

    override def write(rssItems: RssItems): Unit = scala.xml.XML.save(inputFile.toString, Converter.convertRssItemsToXML(rssItems))
}
