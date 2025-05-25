import React, { useState, useEffect } from "react";
import { getSuggestions } from "../api/api";

function SearchBar({ query, setQuery, onSearch }) {
    const [suggestions, setSuggestions] = useState([]);
    const [showSuggestions, setShowSuggestions] = useState(false);

    useEffect(() => {
        if (query.trim() && showSuggestions) {
            getSuggestions(query).then(setSuggestions);
        } else {
            setSuggestions([]);
        }
    }, [query, showSuggestions]);

    const handleInputChange = (e) => {
        setQuery(e.target.value);
        setShowSuggestions(true); 
    };

    const handleSuggestionClick = (s) => {
        setQuery(s);
        setShowSuggestions(false);
    };

    const getIcon = () => {
        if (query.toLowerCase().includes(".py")) return "🐍";
        if (query.toLowerCase().includes(".java")) return "☕";
        if (query.toLowerCase().includes(".txt")) return "📄";
        if (query.toLowerCase().includes(".pdf")) return "📕";
        if (query.toLowerCase().includes(".exe")) return "⚙️";
        return "🔍"; 
    }

    return (
        <div>
            <input
                type="text"
                placeholder="Enter query..."
                value={query}
                onChange={handleInputChange}
            />
            <button onClick={onSearch}>Search {getIcon()} </button>
            {showSuggestions && suggestions.length > 0 && (
                <div>
                    {suggestions.map((s, index) => (
                        <div
                            className="result2"
                            key={index}
                            onClick={() => handleSuggestionClick(s)}
                        >
                            {s}
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default SearchBar;
