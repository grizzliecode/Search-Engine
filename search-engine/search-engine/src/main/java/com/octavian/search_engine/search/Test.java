package com.octavian.search_engine.search;

import com.octavian.search_engine.indexer.file_utilities.file_reader.DocsReader;
import org.apache.poi.EmptyFileException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args)  {
        String path = "D:\\3rd Semester\\EMS\\~$S_Lab_Tables_sensors.docx";
        DocsReader docsReader = new DocsReader();
        try {

        System.out.println(docsReader.getContent(Path.of(path)));
        } catch (IOException e){
            System.out.println("exception");
        } catch (EmptyFileException e){
            System.out.println("exception 2");
        }
    }
}
