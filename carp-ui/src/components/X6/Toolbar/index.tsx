import React, {useCallback} from 'react';
import {
  CopyOutlined, DeleteOutlined,
  RedoOutlined,
  ScissorOutlined,
  UndoOutlined,
  ZoomInOutlined,
  ZoomOutOutlined
} from "@ant-design/icons";
import {useClipboard, useGraphEvent, useGraphStore, useHistory} from "@antv/xflow";
import {Menu, Toolbar} from "@antv/x6-react-components";

const Item = Toolbar.Item // eslint-disable-line
const Divider = Toolbar.Divider // eslint-disable-line
const Group = Toolbar.Group // eslint-disable-line

const X6Toolbar: React.FC = () => {
  const {copy, cut, paste} = useClipboard();
  const {undo, redo, canUndo, canRedo} = useHistory()
  const nodes = useGraphStore((state) => state.nodes);
  const edges = useGraphStore((state) => state.edges);
  const removeNodes = useGraphStore((state) => state.removeNodes);
  const removeEdges = useGraphStore((state) => state.removeEdges);

  const selectNodeIds = useCallback(() => {
    const nodeSelected = nodes.filter((node) => node.selected);
    const nodeIds: string[] = nodeSelected.map((node) => node.id!);
    return nodeIds;
  }, [nodes]);

  const selectEdgeIds = useCallback(() => {
    const edgesSelect = edges.filter((edge) => edge.selected);
    const edgeIds: string[] = edgesSelect.map((edge) => edge.id!);
    return edgeIds;
  }, [edges]);

  const selectShapeIds = () => {
    return [...selectEdgeIds(), ...selectNodeIds()];
  };

  useGraphEvent('node:click', ({node}) => {
    const {data, id} = node;
    console.log('node:click', data, id);
  });

  const renderZoomDropdown = () => {
    return (
      <Menu>
        <Item name="resetView" hotkey="Cmd+H">
          Reset View
        </Item>
        <Item name="fitWindow" hotkey="Cmd+Shift+H">
          Fit Window
        </Item>
        <Divider/>
        <Item name="25">25%</Item>
        <Item name="50">50%</Item>
        <Item name="75">75%</Item>
        <Item name="100">100%</Item>
        <Item name="125">125%</Item>
        <Item name="150">150%</Item>
        <Item name="200">200%</Item>
        <Item name="300">300%</Item>
        <Item name="400">400%</Item>
      </Menu>
    )
  }

  return (
    <Toolbar
      hoverEffect={true}
      extra={<div>extra</div>}
    >
      <Group>
        <Item
          name="zoom"
          tooltipAsTitle={true}
          tooltip="Zoom (Alt+Mousewheel)"
        >
                <span
                  style={{
                    display: 'inline-block',
                    width: 40,
                    textAlign: 'right',
                  }}
                >
                  100%
                </span>
        </Item>
      </Group>
      <Group>
        <Item
          name="zoomIn"
          tooltip="Zoom In (Command ⌘ + Down ⬇︎)"
          icon={<ZoomInOutlined/>}
          onClick={() => {
          }}
        />
        <Item
          name="zoomOut"
          tooltip="Zoom Out (Command ⌘ + Up ⬆︎)"
          icon={<ZoomOutOutlined/>}
        />
      </Group>
      <Group>
        <Item
          name="cut"
          tooltip="Cut (Command ⌘ + X)"
          icon={<ScissorOutlined/>}
          disabled={selectShapeIds() && selectShapeIds().length > 0 ? false : true}
          onClick={() => {
            // fixme 剪切后无法通过 paste 在恢复
            cut(selectShapeIds());
          }}
        />
        <Item
          name="copy"
          tooltip="Copy (Command ⌘ + C)"
          icon={<CopyOutlined/>}
          disabled={selectShapeIds() && selectShapeIds().length > 0 ? false : true}
          onClick={() => {
            copy(selectShapeIds());
          }}
        />
        <Item
          name="paste"
          tooltip="Paste (Command ⌘ + V)"
          icon={<CopyOutlined/>}
          onClick={() => {
            paste();
          }}
        />
      </Group>
      <Group>
        <Item
          name="undo"
          tooltip="Undo (Command ⌘ + Z)"
          icon={<UndoOutlined/>}
          disabled={!canUndo}
          onClick={() => {
            // fixme 不起效
            undo();
          }}
        />
        <Item
          name="redo"
          tooltip="Redo (Command ⌘ + Shift ⇧ + Z)"
          icon={<RedoOutlined/>}
          disabled={!canRedo}
          onClick={() => {
            // fixme 不起效
            redo();
          }}
        />
      </Group>
      <Group>
        <Item
          name="delete"
          tooltip="Delete (Delete)"
          icon={<DeleteOutlined />}
          disabled={selectShapeIds() && selectShapeIds().length > 0 ? false : true}
          onClick={() => {
            removeNodes(selectNodeIds());
            removeEdges(selectEdgeIds());
          }}
        />
      </Group>
    </Toolbar>
  );
};

export default X6Toolbar;
