import React from 'react';
import { DataTable } from 'common/components';

const FacultiesTable = (props) =>
  DataTable({
    url: '/api/faculties',
    responseKey: 'faculties',
    header: ['Nazwa'],
    mapColumns: (faculty) => {
      return [faculty.name];
    },
    ...props,
  });

export default FacultiesTable;
