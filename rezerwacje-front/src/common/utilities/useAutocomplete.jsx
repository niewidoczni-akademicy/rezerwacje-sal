import React from 'react';
import { useEffect, useState } from 'react';

function useAutocomplete(names, fetchPromises) {
  const state = names.reduce(function (map, name) {
    map[name] = [];
    return map;
  }, {});

  const [values, setValues] = useState(state);

  const fetchAndSetValues = async () => {
    let autocompleteEntries = await Promise.all(fetchPromises);
    let newValues = names.reduce(function (map, name, ind) {
      map[name] = autocompleteEntries[ind];
      return map;
    }, {});

    setValues(newValues);
  };

  useEffect(() => {
    fetchAndSetValues();
  }, []);

  return values;
}

export default useAutocomplete;
