import React from "react";

function SearchResults({ results, onOpen }) {
  if (!results.length) return <p>No results found.</p>;

  // Count frequency of extensions
  const extensionCounts = results.reduce((acc, file) => {
    const ext = file.extension || "unknown";
    acc[ext] = (acc[ext] || 0) + 1;
    return acc;
  }, {});

  // Calculate max count for scaling bars
  const maxCount = Math.max(...Object.values(extensionCounts));

  return (
    <div>
      {/* Histogram */}
      <div style={{ marginBottom: "1rem" }}>
        <h3>File Extension Histogram</h3>
        <div style={{ display: "flex", gap: "1rem", alignItems: "flex-end" }}>
          {Object.entries(extensionCounts).map(([ext, count]) => {
            // Bar height scaled to maxCount (max 100px height)
            const height = (count / maxCount) * 100;
            return (
              <div key={ext} style={{ textAlign: "center" }}>
                <div
                  style={{
                    textAlign: "center",
                    height: `${height}px`,
                    width: "20px",
                    backgroundColor: "#4a90e2",
                    marginBottom: "4px",
                    borderRadius: "4px",
                  }}
                  title={`${count} file(s)`}
                ></div>
                <div style={{ fontSize: "0.8rem" }}>{ext}</div>
              </div>
            );
          })}
        </div>
      </div>

      {/* Results */}
      {results.map((file) => (
        <div className="result" key={file.file_id}>
          <strong>{file.file_path}</strong> <br />
          <small>
            {file.extension} • {file.file_size} bytes • Rank: {file.rank_score}
          </small>
          <p>{file.lines.slice(0, 200)}...</p>
          <button onClick={() => onOpen(file.file_path)}>Open</button>
        </div>
      ))}
    </div>
  );
}

export default SearchResults;
