import React from 'react';
import { UploadFilesDialog } from 'common';

const ImportCoursesDialog = (props) =>
  UploadFilesDialog({
    title: 'Import z CSV',
    url: '/api/course-of-study/upload',
    ...props,
  });

export default ImportCoursesDialog;
