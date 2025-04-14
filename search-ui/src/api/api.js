const BASE_URL = "http://localhost:8080";

export const getPreferences = () => fetch(`${BASE_URL}/preferences`).then(res => res.json());

export const setPreferences = (prefs) =>
  fetch(`${BASE_URL}/preferences`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(prefs),
  });

export const getSuggestions = (partial) =>
  fetch(`${BASE_URL}/search/suggestion?partial=${encodeURIComponent(partial)}`).then(res => res.json());

export const searchFiles = (query) =>
  fetch(`${BASE_URL}/search?query=${encodeURIComponent(query)}`).then(res => res.json());

export const openFile = (filePath) =>
  fetch(`${BASE_URL}/search/open?file_path=${encodeURIComponent(filePath)}`, {
    method: "POST",
  });

export const triggerIndex = () =>
  fetch(`${BASE_URL}/index`, { method: "POST" });
