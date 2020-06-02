import React from 'react';
import { DataTable } from 'common/components';

const CoursesTable = (props) => {
  const rows = props.entries.map((course) => {
    const type = course.courseType == 'FULL_TIME' ? 'Stacjonarny' : 'Zaoczny';
    const contactPerson2 =
      course.contactPerson2 != null ? course.contactPerson2.login : '';
    const remarks = course.remarks != null ? course.remarks : '';
    const isJoined = course.isJoined ? 'tak' : 'nie';

    return {
      id: course.id,
      columns: [
        course.name,
        course.faculty.name,
        type,
        course.contactPerson1.login,
        contactPerson2,
        isJoined,
        remarks,
      ],
    };
  });

  return DataTable({
    header: [
      'Kierunek',
      'Wydział',
      'Typ',
      'Kontakt 1',
      'Kontakt 2',
      'Łączony',
      'Uwagi',
    ],
    rows: rows,
    onRowClick: props.onRowClick,
  });
};
export default CoursesTable;
