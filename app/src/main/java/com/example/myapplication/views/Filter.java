package com.example.myapplication.views;
import java.util.ArrayList;

import java.util.List;
public class Filter {
    private FilteringStrategy filteringStrategy;

    public void setFilteringStrategy(FilteringStrategy filteringStrategy) {
        this.filteringStrategy = filteringStrategy;
    }

    public void filter(List<String> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            String item = list.get(i);
            filteringStrategy.satisfies(item, new FilterCallback() {
                @Override
                public void onFilterResult(boolean result) {
                    if (!result) {
                        list.remove(item);
                    }
                }
            });
        }
    }
}

//package com.example.myapplication.views;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Filter {
//
//    private FilteringStrategy filteringStrategy;
//    public void setFilteringStrategy(FilteringStrategy filteringStrategy) {
//        this.filteringStrategy = filteringStrategy;
//    }
//    public void filter(List<String> list) {
//        for (int i = 0; i < list.size(); i++) {
//            if (!filteringStrategy.satisfies(list.get(i))) {
//                list.remove(list.get(i));
//            }
//        }
//    }
//}
