import React, {useCallback} from 'react';
import {useClipboard, useGraphStore, useHistory, useKeyboard} from '@antv/xflow';

const X6Keyboard: React.FC = () => {
  const {copy, cut, paste} = useClipboard();
  const {undo, redo, canUndo, canRedo} = useHistory()
  const nodes = useGraphStore((state) => state.nodes);
  const edges = useGraphStore((state) => state.edges);
  const updateNode = useGraphStore((state) => state.updateNode);
  const updateEdge = useGraphStore((state) => state.updateEdge);
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

  useKeyboard(['meta+x', 'ctrl+x'], () => {
    // fixme 剪切后无法通过 paste 在恢复
    cut(selectShapeIds());
  });

  useKeyboard(['meta+c', 'ctrl+c'], () => {
    copy(selectShapeIds());
  });

  useKeyboard(['meta+v', 'ctrl+v'], () => {
    paste();
  });

  useKeyboard('backspace', () => {
    removeNodes(selectNodeIds());
    removeEdges(selectEdgeIds());
  });

  useKeyboard(['meta+z', 'ctrl+z'], () => {
    if (canUndo) {
      undo();
    }
  });
  useKeyboard(['meta+shift+z', 'ctrl+shift+z'], () => {
    if (canRedo) {
      redo();
    }
  });

  useKeyboard(['meta+a', 'ctrl+a'], () => {
    // fixme 不起效
    nodes.map((node) => updateNode(node.id!, {selected: true}));
    edges.map((edge) => updateEdge(edge.id, {selected: true}));
  });

  return null;
};

export {X6Keyboard};
