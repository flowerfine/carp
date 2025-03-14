import React from 'react';
import {Grid, Snapline} from '@antv/xflow';

const X6GridSnapline: React.FC = () => {

  return (
    <>
      <Grid type="dot"
            size={20}
            options={{
              color: '#ccc', // 网点颜色
              thickness: 1, // 网点大小
            }}
      />
      <Snapline/>
    </>
  );
};

export default X6GridSnapline;
