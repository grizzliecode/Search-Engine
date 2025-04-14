import React, { useState, useEffect } from "react";
import "./index.css";
import Preferences from "./components/Preferences";
import SearchBar from "./components/SearchBar";
import SearchResults from "./components/SearchResults";
import IndexButton from "./components/IndexButton";
import { searchFiles, openFile } from "./api/api";

function App() {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);

  const handleSearch = async () => {
    if (!query.trim()) return;
    const data = await searchFiles(query);
    setResults(data);
  };

  const handleOpen = (filePath) => {
    openFile(filePath);
  };

  return (
    <div className="center">
      <h1>🔍 File Search App</h1>
      <Preferences />
      <SearchBar query={query} setQuery={setQuery} onSearch={handleSearch} />
      <SearchResults results={results} onOpen={handleOpen} />
      <IndexButton />
    </div>
  );
}

export default App;
