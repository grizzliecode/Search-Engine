package com.octavian.search_engine.search.widget;

import com.octavian.search_engine.search.SearchModel;
import com.octavian.search_engine.search.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class WidgetController {
    @Autowired
    private SearchRepository searchRepository;

    @GetMapping("/show_exe")
    public void showExe(@RequestParam List<String> results) throws Exception {
        for(String result: results)
            if(result.contains(".exe"))
                ExecutableProcessor.showExeHeaderWindow(result);
    }
    //TODO
    void  nothing(){

    }
    @GetMapping("/show_text")
    public void showText(@RequestParam List<String> results) throws Exception{
        for (String result: results){
            if(result.contains(".pdf") || result.contains(".docs") || result.contains(".txt")){
                String inputString = this.searchRepository.getContent(result);
                TextSummarizer.showSummaryWindow(inputString);
            }
        }
    }
}
