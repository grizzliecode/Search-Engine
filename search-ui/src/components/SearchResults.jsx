import React from "react";

function SearchResults({ results, onOpen }) {
  if (!results.length) return <p>No results found.</p>;

  return (
    <div>
      {results.map((file) => (
        <div className="result" key={file.file_id}>
          <strong>{file.file_path}</strong> <br />
          <small>{file.extension} • {file.file_size} bytes • Rank: {file.rank_score}</small>
          <p>{file.lines.slice(0, 200)}...</p>
          <button onClick={() => onOpen(file.file_path)}>Open</button>
        </div>
      ))}
    </div>
  );
}

export default SearchResults;
