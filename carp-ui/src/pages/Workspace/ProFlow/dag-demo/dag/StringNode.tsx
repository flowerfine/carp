import { FC } from 'react';
import { Handle, Position } from '@ant-design/pro-flow';
import useStyles from './style';

export const StringRender: FC = (node: any) => {
  const { handles, id, selected } = node;
  const { styles, cx } = useStyles();

  return (
    <div className={cx(styles.stringNode, selected && styles.selected)}>
      <Handle
        id={typeof handles?.target === 'string' ? handles?.target : id}
        type={'target'}
        position={Position.Left}
      />
      {node.data.title}
      <Handle
        id={typeof handles?.source === 'string' ? handles?.source : id}
        type={'source'}
        position={Position.Right}
      />
    </div>
  );
};
