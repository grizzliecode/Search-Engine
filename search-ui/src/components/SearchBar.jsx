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
        setShowSuggestions(true); // Enable suggestion display on input change
    };

    const handleSuggestionClick = (s) => {
        setQuery(s);
        setShowSuggestions(false); // Hide suggestions when one is clicked
    };

    return (
        <div>
            <input
                type="text"
                placeholder="Enter query..."
                value={query}
                onChange={handleInputChange}
            />
            <button onClick={onSearch}>Search</button>
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
