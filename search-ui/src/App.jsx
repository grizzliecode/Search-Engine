import React, { useState } from "react";
import "./index.css";
import Preferences from "./components/Preferences";
import SearchBar from "./components/SearchBar";
import SearchResults from "./components/SearchResults";
import IndexButton from "./components/IndexButton";
import { searchFiles, openFile, showText, showExe } from "./api/api";

function App() {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [widgetType, setWidgetType] = useState("EMPTY"); // new state for widgetType
  const [file_paths, setFilePaths] = useState([]);  // new state for file paths list
  const [widgetContent, setWidgetContent] = useState(""); // state for widget response text

  const handleSearch = async () => {
    if (!query.trim()) return;

    try {
      const data = await searchFiles(query);
      // data expected shape: { widgetType: string, results: SearchModel[] }
      setWidgetType(data.widgetType || "EMPTY");
      setResults(data.results || []);
      setFilePaths((data.results || []).map(item => item.file_path));
      setWidgetContent(""); // clear previous widget content on new search
    } catch (error) {
      console.error("Error during search:", error);
      setWidgetType("EMPTY");
      setResults([]);
      setFilePaths([]);
      setWidgetContent("");
    }
  };

  const handleOpen = (filePath) => {
    openFile(filePath);
  };

  const handleWidgetClick = async () => {
    if (widgetType === "TEXT") {
      try {
        const text = await showText(file_paths);
        setWidgetContent(text);
      } catch (err) {
        console.error("Error fetching text widget data:", err);
        setWidgetContent("Error loading content");
      }
    } else if (widgetType === "EXE") {
      try {
        const text = await showExe(file_paths);
        setWidgetContent(text);
      } catch (err) {
        console.error("Error fetching exe widget data:", err);
        setWidgetContent("Error loading content");
      }
    }
  };

  return (
    <div className="center">
      <h1>🔍 File Search App</h1>
      <Preferences />
      <SearchBar query={query} setQuery={setQuery} onSearch={handleSearch} />
      <SearchResults results={results} onOpen={handleOpen} />

      {/* Widget Section */}
      {widgetType !== "EMPTY" && (
        <div style={{ marginTop: "1rem" }}>
          <button onClick={handleWidgetClick}>
            {widgetType === "TEXT" && "Show Text Content"}
            {widgetType === "EXE" && "Show EXE Content"}
            {widgetType !== "TEXT" && widgetType !== "EXE" && "Show Widget"}
          </button>
          <pre
            style={{
              marginTop: "0.5rem",
              padding: "1rem",
              border: "1px solid #ccc",
              whiteSpace: "pre-wrap",
              maxHeight: "300px",
              overflowY: "auto",
              backgroundColor: "#f9f9f9",
            }}
          >
            {widgetContent}
          </pre>
        </div>
      )}

      <IndexButton />
    </div>
  );
}

export default App;
