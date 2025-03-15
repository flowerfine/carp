import React from 'react';
import {Clipboard, History, useClipboard, useGraphStore, useHistory, useKeyboard} from '@antv/xflow';

const X6HistoryClipboard: React.FC = () => {
  const {copy, cut, paste} = useClipboard();
  const {undo, redo, canUndo, canRedo} = useHistory()
  const nodes = useGraphStore((state) => state.nodes);
  const edges = useGraphStore((state) => state.edges);
  const removeNodes = useGraphStore((state) => state.removeNodes);
  const removeEdges = useGraphStore((state) => state.removeEdges);

  useKeyboard('command+c', () => onCopy());
  useKeyboard('command+v', () => onPaste());
  useKeyboard('backspace', (e: KeyboardEvent) => onDelete(e));

  const onUndo = () => {
    // fixme 不起效
    if (canUndo) {
      undo()
    }
  };

  const onRedo = () => {
    // fixme 不起效
    if (canRedo) {
      redo()
    }
  };

  const onCut = () => {
    const selected = nodes.filter((node) => node.selected);
    const ids: string[] = selected.map((node) => node.id || '');
    // fixme 剪切后无法粘贴出来
    cut(ids)
  };

  const onCopy = () => {
    const selected = nodes.filter((node) => node.selected);
    const ids: string[] = selected.map((node) => node.id || '');
    copy(ids);
  };

  const onPaste = () => {
    paste();
  };

  const onDelete = (e: KeyboardEvent) => {
    console.log('onDelete')
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
