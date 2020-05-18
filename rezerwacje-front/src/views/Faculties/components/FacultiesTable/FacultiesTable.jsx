import React from 'react';
import { DataTable } from 'common/components';

// TODO: backend...
const FacultiesTable = (props) =>
  DataTable({
    url: '/api/rooms',
    responseKey: 'rooms',
    header: ['Sala', 'Budynek', 'Pojemność'],
    mapColumns: (room) => {
      return [room.name, room.building, room.capacity];
    },
    ...props,
  });

export default FacultiesTable;
