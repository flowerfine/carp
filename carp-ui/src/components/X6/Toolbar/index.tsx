import React from 'react';
import {Toolbar} from "@antv/x6-react-components";

const X6Toolbar: React.FC = () => {

  return (
    <Toolbar extra={<div>extra</div>}>
      <div>toolbar</div>
    </Toolbar>
  );
};

export default X6Toolbar;
