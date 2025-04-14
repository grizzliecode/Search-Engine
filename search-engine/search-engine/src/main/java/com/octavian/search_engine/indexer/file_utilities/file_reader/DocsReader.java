package com.octavian.search_engine.indexer.file_utilities.file_reader;

import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class DocsReader implements ContentReader{

    @Override
    public String getContent(Path path) throws IOException, EmptyFileException {
        try {
            FileInputStream fis = new FileInputStream(path.toFile());
            XWPFDocument document = new XWPFDocument(fis);
            StringBuilder content = new StringBuilder();
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                content.append(paragraph.getText()).append("\n");
            }
            document.close();
            fis.close();
            return content.toString();
        }catch (NotOfficeXmlFileException e){
            throw new IOException(e.getMessage());
        }
    }
}
