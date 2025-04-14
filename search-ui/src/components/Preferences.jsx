import React, { useEffect, useState } from "react";
import { getPreferences, setPreferences } from "../api/api";

function Preferences() {
  const [prefs, setPrefs] = useState({ path: "", ignored_paths: [] });

  useEffect(() => {
    getPreferences().then(setPrefs);
  }, []);

  const handleSave = () => {
    setPreferences(prefs);
  };

  return (
    <div>
      <h3>Preferences</h3>
      <label>Path: </label>
      <input
        type="text"
        value={prefs.path}
        onChange={(e) => setPrefs({ ...prefs, path: e.target.value })}
      />
      <br />
      <label>Ignored Paths:</label>
      <br></br>
      <textarea
        rows="5"
        value={prefs.ignored_paths.join("\n")}
        onChange={(e) =>
          setPrefs({ ...prefs, ignored_paths: e.target.value.split("\n") })
        }
      />
      <br />
      <button onClick={handleSave}>Save Preferences</button>
    </div>
  );
}

export default Preferences;
