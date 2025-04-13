package com.octavian.search_engine.indexer.file_utilities.file_reader;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class DocsReader implements ContentReader{

    @Override
    public String getContent(Path path) throws IOException {
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
    }
}
