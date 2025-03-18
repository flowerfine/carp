import React, {useCallback, useState} from 'react';
import {
  CopyOutlined,
  DeleteOutlined,
  RedoOutlined,
  ScissorOutlined,
  UndoOutlined,
  ZoomInOutlined,
  ZoomOutOutlined
} from "@ant-design/icons";
import {useClipboard, useGraphEvent, useGraphInstance, useGraphStore, useHistory} from "@antv/xflow";
import {Menu, Toolbar} from "@antv/x6-react-components";

const Item = Toolbar.Item // eslint-disable-line
const Group = Toolbar.Group // eslint-disable-line

enum ControlEnum {
  ZoomTo = 'zoomTo',
  ZoomIn = 'zoomIn',
  ZoomOut = 'zoomOut',
  ZoomToFit = 'zoomToFit',
  ZoomToOrigin = 'zoomToOrigin',
}

const X6Toolbar: React.FC = () => {
  const graph = useGraphInstance();
  const {copy, cut, paste} = useClipboard();
  const {undo, redo, canUndo, canRedo} = useHistory()
  const nodes = useGraphStore((state) => state.nodes);
  const edges = useGraphStore((state) => state.edges);
  const removeNodes = useGraphStore((state) => state.removeNodes);
  const removeEdges = useGraphStore((state) => state.removeEdges);

  const [zoom, setZoom] = useState(1);


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
  });

  useGraphEvent('scale', ({sx}) => {
    setZoom(sx);
  });

  const changeZoom = (type: ControlEnum, args?: string) => {
    if (!graph) return;
    const key = parseInt(args || '1', 10);
    const zoomNum = (0.25 * (key + 1)) as number;
    switch (type) {
      case ControlEnum.ZoomIn:
        if (zoom < 1.5) {
          graph.zoom(0.25);
        }
        break;
      case ControlEnum.ZoomOut:
        if (zoom > 0.5) {
          graph.zoom(-0.25);
        }
        break;
      case ControlEnum.ZoomToFit:
        graph.zoomToFit({maxScale: 1});
        break;
      case ControlEnum.ZoomToOrigin:
        graph.zoomTo(1);
        break;
      case ControlEnum.ZoomTo:
        graph.zoomTo(zoomNum);
        break;
      default:
        break;
    }
    setZoom(graph.zoom());
  };

  const isToolButtonEnabled = (type: ControlEnum) => {
    if (type == ControlEnum.ZoomIn) {
      return zoom < 1.5;
    } else if (type === ControlEnum.ZoomOut) {
      return zoom > 0.51;
    }
    return true;
  };

  const renderZoomDropdown = () => {
    const MenuItem = Menu.Item // eslint-disable-line
    const Divider = Menu.Divider // eslint-disable-line
    return (
      <Menu>
        <MenuItem name={"resetView"} text={"重置"} hotkey={"Command ⌘ + H"}
                  onClick={() => changeZoom(ControlEnum.ZoomToOrigin)}/>
        <MenuItem name={"fitWindow"} text={"自适应窗口大小"} hotkey="Command ⌘ + Shift ⇧ + H"
                  onClick={() => changeZoom(ControlEnum.ZoomToFit)}/>
        <Divider/>
        <MenuItem name="50" onClick={() => changeZoom(ControlEnum.ZoomTo, '1')}>50%</MenuItem>
        <MenuItem name="75" onClick={() => changeZoom(ControlEnum.ZoomTo, '2')}>75%</MenuItem>
        <MenuItem name="100" onClick={() => changeZoom(ControlEnum.ZoomTo, '3')}>100%</MenuItem>
        <MenuItem name="125" onClick={() => changeZoom(ControlEnum.ZoomTo, '4')}>125%</MenuItem>
        <MenuItem name="150" onClick={() => changeZoom(ControlEnum.ZoomTo, '5')}>150%</MenuItem>
      </Menu>
    )
  }

  return (
    <Toolbar hoverEffect={true}>
      <Group>
        <Item
          name="zoom"
          tooltipAsTitle={true}
          tooltip="Zoom (Alt+Mousewheel)"
          dropdown={renderZoomDropdown()}
        >
                <span
                  style={{
                    display: 'inline-block',
                    width: 40,
                    textAlign: 'right',
                  }}
                >
                  {`${Math.floor(zoom * 100)}%`}
                </span>
        </Item>
      </Group>
      <Group>
        <Item
          name="zoomIn"
          tooltip="Zoom In (Command ⌘ + Down ⬇︎)"
          icon={<ZoomInOutlined/>}
          disabled={!isToolButtonEnabled(ControlEnum.ZoomIn)}
          onClick={(name) => changeZoom(ControlEnum.ZoomIn)}
        />
        <Item
          name="zoomOut"
          tooltip="Zoom Out (Command ⌘ + Up ⬆︎)"
          icon={<ZoomOutOutlined/>}
          disabled={!isToolButtonEnabled(ControlEnum.ZoomOut)}
          onClick={(name) => changeZoom(ControlEnum.ZoomOut)}
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
          icon={<DeleteOutlined/>}
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
