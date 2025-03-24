package com.octavian.search_engine.preferences;

import java.util.ArrayList;
import java.util.List;

public record Preferences(String path, List<String> ignored_paths) {
}
