import React from 'react';
import { DataTable } from 'common/components';

const RoomsTable = (props) => {
  const rows = props.entries.map((room) => {
    return { id: room.id, columns: [room.name, room.building, room.capacity] };
  });

  return DataTable({
    header: ['Sala', 'Budynek', 'Pojemność'],
    rows: rows,
    onRowClick: props.onRowClick,
  });
};

export default RoomsTable;
