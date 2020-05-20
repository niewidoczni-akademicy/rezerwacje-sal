import React from 'react';
import { UploadFilesDialog } from 'common';

const ImportRoomsDialog = (props) =>
  UploadFilesDialog({
    title: 'Import z CSV',
    url: '/api/rooms/upload',
    ...props,
  });

export default ImportRoomsDialog;
