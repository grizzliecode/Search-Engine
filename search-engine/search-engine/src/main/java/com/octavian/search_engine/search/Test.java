package com.octavian.search_engine.search;

import com.octavian.search_engine.indexer.file_utilities.FileHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) {
        System.out.println(FileHandler.getPathEntropy(Path.of("aaaaa")));
        System.out.println(FileHandler.getPathEntropy(Path.of("aaaab")));
        System.out.println(FileHandler.getPathEntropy(Path.of("aaabbjcwbrjbevdmao")));
        System.out.println(FileHandler.getPathEntropy(Path.of("D:\\6th semester\\FLT\\Laboratories\\cjab_dvuwi2e0\\.kcbushrv")));
        System.out.println(FileHandler.getPathEntropy(Path.of("D:\\6th semester\\FLT\\Laboratories\\cjabdvuwi2e0\\.kcbushrv\\pmxbuf954\\64bfghtqwer\\ajs0-saj(1)\\wrtyhxzlxk\\acmoid os.txt")));
        System.out.println(Path.of("D:\\6th semester\\FLT\\Laboratories\\cjabdvuwi2e0\\.kcbushrv\\pmxbuf954\\64bfghtqwer\\ajs0-saj(1)\\wrtyhxzlxk\\acmoid os.txt").getNameCount());
        System.out.println(FileHandler.getPathEntropy(Path.of("D:\\6th semester\\cv_vlad_roatis___2025 (1).pdf")));
    }
}
