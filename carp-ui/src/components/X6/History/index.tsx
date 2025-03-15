import React from 'react';
import {Clipboard, History, useClipboard, useGraphStore, useHistory, useKeyboard} from '@antv/xflow';

const X6HistoryClipboard: React.FC = () => {
  const {copy, paste} = useClipboard();
  const {undo, redo, canUndo, canRedo} = useHistory()
  const nodes = useGraphStore((state) => state.nodes);
  const edges = useGraphStore((state) => state.edges);
  const removeNodes = useGraphStore((state) => state.removeNodes);
  const removeEdges = useGraphStore((state) => state.removeEdges);

  useKeyboard('command+z', () => onUndo());
  useKeyboard('command+c', () => onCopy());
  useKeyboard('command+v', () => onPaste());
  useKeyboard('backspace', () => onDelete);

  const onUndo = () => {
    console.log('undo', undo, canUndo)
    if (canUndo) {
      undo()
    }
  };

  const onCopy = () => {
    const selected = nodes.filter((node) => node.selected);
    const ids: string[] = selected.map((node) => node.id || '');
    copy(ids);
  };

  const onPaste = () => {
    paste();
  };

  const onDelete = () => {
    const selectedNodes = nodes.filter((node) => node.selected);
    const nodeIds: string[] = selectedNodes.map((node) => node.id || '');
    removeNodes(nodeIds);
    const selectedEdges = edges.filter((edge) => edge.selected);
    const edgeIds: string[] = selectedEdges.map((edge) => edge.id || '');
    removeEdges(edgeIds);
  };

  return (
    <>
      <History/>
      <Clipboard/>
    </>
  );
};

export default X6HistoryClipboard;
