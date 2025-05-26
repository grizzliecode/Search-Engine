# 25 MAY 2025
---
 Search component:
 - refactored the query history in memory cache so that it can be used by the facade cache as well;  
 - implemented a cache for the search queries 
 - added contextual widgets, via a WidgetFactory
 - modified the search results so that they retrieve the widget as well
 
 Spelling corrector:
 - added a spelling corrector component which is called by the front end similar to the query suggestion, i.e., after the search term has been modified

 FrontEnd: 
 - modified the use of the search result api call
 - added visual widgets for search and functional context widgets for the results
 - added histogram for the results


# 13 April 2025
---

Indexer:
- added rank score for the files indexed by the app
- added support for executable files
- rank calculated using heuristics


Search component:  
- added querry enhancer via the observer pattern
- added endpoint for suggesting queries based on a querry history

Frontend:  
- added frontend using react

Database:  
- modified the database schema reduced it to a single table with the second table neing transformed to an index

Logger:  
- added a new type of logger

# 12 April 2025

Indexer:  
- refactored the way files are processed by using the strategy pattern

# 24 March 2025

Indexer component was created.
Search component was created. It also contains an endpoint for opening files.
Preferences component was created.
Logger component was created.

# 17 March 2025
 
 An initial design was scetched.(using the C4 model).