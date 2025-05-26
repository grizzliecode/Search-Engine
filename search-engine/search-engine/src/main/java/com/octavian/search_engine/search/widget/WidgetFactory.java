package com.octavian.search_engine.search.widget;

import com.octavian.search_engine.search.SearchModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WidgetFactory {
    private final List<Widget> allWidgets = List.of(
            new EmptyWidget(),
            new ExecutableStatisticWidget(),
            new TextSummarizationWidget()
    );

    public Widget getBestFit(String query, List<SearchModel> results){
        return allWidgets.stream()
                .sorted((w1,w2) -> Float.compare(w2.activationScore(query,results), w1.activationScore(query,results)))
                .findFirst()
                .orElse(new EmptyWidget());
    }
}
