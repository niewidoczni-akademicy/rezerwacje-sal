import React from 'react';
import { DataTable } from 'common/components';

const FacultiesTable = (props) => {
  const rows = props.entries.map((faculty) => {
    return {
      id: faculty.id,
      columns: [faculty.name],
    };
  });

  return DataTable({
    header: ['Nazwa'],
    rows: rows,
    onRowClick: props.onRowClick,
  });
};
export default FacultiesTable;
