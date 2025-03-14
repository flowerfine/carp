import React, {useEffect, useState} from 'react';
import {Button, Descriptions, Popover, Tree, Typography} from 'antd';
import {DatabaseFilled, HolderOutlined, InfoCircleOutlined} from "@ant-design/icons";
import SearchInput from './search';
import styles from './style.less';

type PanelProps = {
  request: () => Promise<X6API.DndGroup>;
  onDrag: (e: React.MouseEvent<Element, MouseEvent>, data: X6API.DndNode) => void;
  onSearch: (keyword: string) => Promise<X6API.DndGroup>;
}

const X6Panel: React.FC = ({request, onDrag, onSearch}: PanelProps) => {
  const [treeItems, setTreeItems] = useState<X6API.DndGroup[]>([]);
  const [searchComponents, setSearchComponents] = useState<X6API.DndGroup[]>([]);

  useEffect(() => {
    request().then((response) => {
      if (response) {
        setTreeItems(response)
      }
    })
  }, []);

  const handleSearchComponent = (keyword?: string) => {
    if (!keyword) {
      setSearchComponents([]);
      return;
    }
    if (onSearch) {
      onSearch(keyword).then((response) => {
        if (response) {
          setSearchComponents(response)
        }
      })
    } else {
      const searchResult = treeItems.flatMap((group) =>
        group.children.filter((child) =>
          child.title.toLowerCase().includes(keyword.toLowerCase()),
        ),
      );
      setSearchComponents(searchResult);
    }
  };

  const treeNodeRender = (treeNode: any) => {
    if (treeNode.children) {
      return <span className={styles.dir}>{treeNode.label}</span>;
    } else {
      return (
        <Popover
          content={
            <>
              <Descriptions style={{maxWidth: '380px'}} size="small" column={1}>
                <Descriptions.Item>{treeNode.dndMeta?.description ? treeNode.dndMeta?.description : treeNode.label}</Descriptions.Item>
              </Descriptions>
            </>
          }
          title={
            <div>
              <Typography.Text>{treeNode.label}</Typography.Text>
              {treeNode.dndMeta?.document && (
                <a href={treeNode.dndMeta?.document} target="_blank">
                  <Button shape="default" type="link" icon={<InfoCircleOutlined/>}/>
                </a>
              )}
            </div>
          }
          placement="right"
        >
          <div
            className={styles.node}
            onMouseDown={(e) => onDrag(e, treeNode)}
          >
            <div className={styles.nodeTitle}>
              <span className={styles.icon}>
                <DatabaseFilled style={{color: '#A1AABC'}}/>
              </span>
              <span>{treeNode.label}</span>
            </div>
            <div className={styles.nodeDragHolder}>
              <HolderOutlined/>
            </div>
          </div>
        </Popover>
      );
    }
  };

  return (
    <div className={styles.components}>
      <div className={styles.action}>
        <SearchInput
          className={styles.search}
          placeholder="请输入搜索关键字"
          onSearch={(key) => handleSearchComponent(key)}
        />
      </div>
      {treeItems.length && (
        <Tree
          rootClassName={styles.tree}
          blockNode
          showIcon={false}
          key={"key"}
          defaultExpandAll
          titleRender={(node) => treeNodeRender(node)}
          treeData={searchComponents.length ? searchComponents : treeItems}
        />
      )}
    </div>
  );
};

export default X6Panel;
