export const convertType = type => {
  if (type == "FULL_TIME") return "stacjonarne";
  else return "zaoczne";
};

export const convertDegree = degree => {
  if (degree == "FIRST_DEGREE") return "I stopień";
  else return "II stopień";
};
