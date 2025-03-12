import React, {useEffect} from 'react';
import {Popover, Tree} from 'antd';
import {DatabaseFilled, HolderOutlined} from '@ant-design/icons';
import {useDnd, useGraphInstance} from '@antv/xflow';
import styles from './dnd.less';
import SearchInput from './search';
import {CellStatus, PROCESS_NODE} from "@/components/Flow/Node/ProcessNode";

const {DirectoryTree} = Tree;

type ComponentTreeItem = {
  category: string;
  docString: string;
  isLeaf: boolean;
  key: string;
  title: string;
  children?: ComponentTreeItem[];
  ports?: {
    id: string;
    group: string;
  }[];
};

const componentTreeData = [
  {
    category: '',
    docString: '',
    isLeaf: false,
    key: '数据输入',
    title: '数据输入',
    children: [
      {
        category: '数据输入',
        docString: '数据输入组件',
        isLeaf: true,
        key: 'INPUT',
        title: '数据输入',
        ports: [
          {
            id: 'INPUT-bottom',
            group: 'out',
          },
        ],
      }
    ],
  },
  {
    category: '',
    docString: '',
    isLeaf: false,
    key: '数据输出',
    title: '数据输出',
    children: [
      {
        category: '数据输出',
        docString: '数据输出组件',
        isLeaf: true,
        key: 'OUTPUT',
        title: '数据输出',
        ports: [
          {
            id: 'OUTPUT-bottom',
            group: 'in',
          },
        ],
      }
    ],
  },
  {
    category: '',
    docString: '',
    isLeaf: false,
    key: '数据处理',
    title: '数据处理',
    children: [
      {
        category: '数据处理',
        docString: '数据过滤',
        isLeaf: true,
        key: 'FILTER',
        title: '数据过滤',
        ports: [
          {
            id: 'FILTER-top',
            group: 'in',
          },
          {
            id: 'FILTER-bottom',
            group: 'out',
          },
        ],
      },
      {
        category: '数据处理',
        docString: '数据连接',
        isLeaf: true,
        key: 'JOIN',
        title: '数据连接',
        ports: [
          {
            id: 'JOIN-top',
            group: 'in',
          },
          {
            id: 'JOIN-bottom',
            group: 'out',
          },
        ],
      },
      {
        category: '数据处理',
        docString: '数据合并',
        isLeaf: true,
        key: 'UNION',
        title: '数据合并',
        ports: [
          {
            id: 'UNION-top',
            group: 'in',
          },
          {
            id: 'UNION-bottom',
            group: 'out',
          },
        ],
      },
      {
        category: '数据处理',
        docString: '数据聚合',
        isLeaf: true,
        key: 'AGG',
        title: '数据聚合',
        ports: [
          {
            id: 'AGG-top',
            group: 'in',
          },
          {
            id: 'AGG-bottom',
            group: 'out',
          },
        ],
      }
    ],
  },
];

const Dnd = () => {
  const graph = useGraphInstance();
  const {startDrag} = useDnd();

  useEffect(() => {
    if (graph) {

    }
  }, [graph]);

  let id = 0;


  const handleMouseDown = (
    e: React.MouseEvent<Element, MouseEvent>,
    item: ComponentTreeItem,
  ) => {
    id += 1;
    const node = {
      id: id.toString(),
      shape: PROCESS_NODE,
      data: {
        type: item.key,
        name: item.title + "_" + id,
        status: CellStatus.DEFAULT,
      },
      ports: item.ports,
      tools: [
        {
          name: "button-remove",
          args: {
            x: "100%",
            y: 0,
            offset: {x: -35, y: 0}
          }
        }
      ],
    }
    startDrag(node, e);
  };

  const [searchComponents, setSearchComponents] = React.useState<ComponentTreeItem[]>(
    [],
  );

  const handleSearchComponent = (keyword?: string) => {
    if (!keyword) {
      setSearchComponents([]);
      return;
    }
    const searchResult = componentTreeData.flatMap((group) =>
      group.children.filter((child) =>
        child.title.toLowerCase().includes(keyword.toLowerCase()),
      ),
    );
    setSearchComponents(searchResult);
  };

  const treeNodeRender = (treeNode: ComponentTreeItem) => {
    const {isLeaf, docString, title} = treeNode;
    if (isLeaf) {
      return (
        <Popover
          content={
            <div
              style={{
                width: 200,
                wordWrap: 'break-word',
                whiteSpace: 'pre-wrap',
                overflow: 'auto',
              }}
            >
              {docString}
            </div>
          }
          placement="right"
        >
          <div
            className={styles.node}
            onMouseDown={(e) => handleMouseDown(e, treeNode)}
          >
            <div className={styles.nodeTitle}>
              <span className={styles.icon}>
                <DatabaseFilled style={{color: '#A1AABC'}}/>
              </span>
              <span>{title}</span>
            </div>
            <div className={styles.nodeDragHolder}>
              <HolderOutlined/>
            </div>
          </div>
        </Popover>
      );
    } else {
      return <span className={styles.dir}>{title}</span>;
    }
  };

  return (
    <div className={styles.components}>
      <div className={styles.action}>
        <SearchInput
          className={styles.search}
          placeholder="请输入搜索关键字"
          onSearch={(key) => handleSearchComponent(key)}
        ></SearchInput>
      </div>
      {componentTreeData.length && (
        <DirectoryTree
          rootClassName={styles.tree}
          blockNode
          showIcon={false}
          defaultExpandAll
          titleRender={(node) => treeNodeRender(node)}
          treeData={searchComponents.length ? searchComponents : componentTreeData}
        ></DirectoryTree>
      )}
    </div>
  );
};

export {Dnd};
