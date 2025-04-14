import React from "react";
import { triggerIndex } from "../api/api";

function IndexButton() {
  const handleIndex = () => {
    triggerIndex().then(() => alert("Indexing started!"));
  };

  return (
    <div style={{ marginTop: "20px" }}>
      <button onClick={handleIndex}>Run Indexing</button>
    </div>
  );
}

export default IndexButton;
