import React from 'react';
import {Menubar} from "@antv/x6-react-components";

const X6Menubar: React.FC = () => {

  return (
    <Menubar extra={<div>extra</div>}>
      <div>menubar</div>
    </Menubar>
  );
};

export default X6Menubar;
