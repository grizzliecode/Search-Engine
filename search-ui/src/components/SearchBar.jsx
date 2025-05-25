import React, { useState, useEffect } from "react";
import { getSuggestions, getSpellingSuggestions } from "../api/api";

function SearchBar({ query, setQuery, onSearch }) {
    const [suggestions, setSuggestions] = useState([]);
    const [showSuggestions, setShowSuggestions] = useState(false);
    const [showSpelling, setShowSpelling] = useState(false);
    const [spelling, setSpelling] = useState(null);

    useEffect(() => {
        if (query.trim() && showSuggestions) {
            getSuggestions(query).then(setSuggestions);
        } else {
            setSuggestions([]);
        }
    }, [query, showSuggestions]);

    useEffect(() => {
        if (query.trim() && showSpelling) {
            getSpellingSuggestions(query)
                .then(setSpelling);
        } else {
            setSpelling(null);
        }
    }, [query, showSpelling]);
    

    const handleInputChange = (e) => {
        setQuery(e.target.value);
        setShowSuggestions(true);
        setShowSpelling(true);
    };

    const handleSuggestionClick = (s) => {
        setQuery(s);
        setShowSuggestions(false);
    };

    const handleSpellingClick = (correction) => {
        setQuery(correction);
        setShowSpelling(false);
    };

    const getIcon = () => {
        if (query.toLowerCase().includes(".py")) return "🐍";
        if (query.toLowerCase().includes(".java")) return "☕";
        if (query.toLowerCase().includes(".txt")) return "📄";
        if (query.toLowerCase().includes(".pdf")) return "📕";
        if (query.toLowerCase().includes(".exe")) return "⚙️";
        return "🔍"; 
    };

    return (
        <div>
            <input
                type="text"
                placeholder="Enter query..."
                value={query}
                onChange={handleInputChange}
            />
            <button onClick={onSearch}>Search {getIcon()} </button>

            {showSpelling && spelling && (
                <div 
                    style={{ cursor: "pointer", color: "red" , border: "1px solid #ccc", padding: "5px", marginTop: "5px" }}
                    onClick={() => handleSpellingClick(spelling)}
                >
                    Did you mean: <b>{spelling}</b>?
                </div>
            )}

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
