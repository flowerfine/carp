import React from 'react';
import {Clipboard, History, useClipboard, useGraphStore, useKeyboard} from '@antv/xflow';

const X6HistoryClipboard: React.FC = () => {
  const {copy, paste} = useClipboard();
  const nodes = useGraphStore((state) => state.nodes);
  const edges = useGraphStore((state) => state.edges);
  const removeNodes = useGraphStore((state) => state.removeNodes);
  const removeEdges = useGraphStore((state) => state.removeEdges);

  useKeyboard('command+c', () => onCopy());
  useKeyboard('command+v', () => onPaste());
  useKeyboard('backspace', (e: KeyboardEvent) => {
    const selectedNodes = nodes.filter((node) => node.selected);
    const nodeIds: string[] = selectedNodes.map((node) => node.id || '');
    removeNodes(nodeIds);
    const selectedEdges = edges.filter((edge) => edge.selected);
    const edgeIds: string[] = selectedEdges.map((edge) => edge.id || '');
    removeEdges(edgeIds);
  });

  const onUndo = () => {
    if (graph?.canUndo) {
      graph.undo()
    }
  };

  const onRedo = () => {
    if (graph?.canRedo()) {
      graph?.redo()
    }
  };

  const onCopy = () => {
    const selected = nodes.filter((node) => node.selected);
    console.log('selected', selected)
    const ids: string[] = selected.map((node) => node.id || '');
    copy(ids);
  };

  const onPaste = () => {
    paste();
  };

  const onDelete = () => {
    const selected = nodes.filter((node) => node.selected);
    const ids: string[] = selected.map((node) => node.id || '');
    removeNodes(ids);
  };

  return (
    <>
      <History/>
      <Clipboard/>
    </>
  );
};

export default X6HistoryClipboard;
