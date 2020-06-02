import React from 'react';
import { useEffect, useState } from 'react';

// Hook to maintain and search list of displayed entries
function useEntryList(url, responseKey) {
  const [entries, setEntries] = useState([]);

  const findEntry = (id) => {
    for (var i = 0; i < entries.length; ++i)
      if (entries[i].id === id) return entries[i];
    return null;
  };

  useEffect(() => {
    fetch(url)
      .then((res) => res.json())
      .then((json) => {
        const entryList = json[responseKey];
        setEntries(entryList);
      })
      .catch((e) => console.log(e));
  }, []);

  return {
    entries,
    findEntry,
  };
}

export default useEntryList;
