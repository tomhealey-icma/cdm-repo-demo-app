package com.finxis.cdm.crossproductapp.util;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class DocumentToFile {


    public void writeWc3DocumentToFile(Document doc) throws TransformerException, IOException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        java.io.FileWriter writer = new FileWriter(new File("document.xml"));
        StreamResult result = new StreamResult(writer);

        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);


    }

    public String writeWc3DocumentToString(Document doc) throws TransformerException, IOException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        DOMSource source = new DOMSource(doc);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        writer.flush();

        return writer.toString();
    }
}
