package com.octavian.search_engine.indexer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import java.nio.file.Path;

public class PDFReader implements ContentReader{
    @Override
    public String getContent(Path path) throws IOException {
        PDDocument document = PDDocument.load(path.toFile());
        PDFTextStripper textStripper = new PDFTextStripper();
        String content = textStripper.getText(document);
        document.close();
        return content;
    }
}
