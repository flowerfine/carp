import React from 'react';
import {Clipboard, History} from '@antv/xflow';
import {X6Keyboard} from "@/components/X6/History/Keyboard";

const X6HistoryClipboard: React.FC = () => {

  return (
    <>
      <History/>
      <Clipboard/>
      <X6Keyboard/>
    </>
  );
};

export default X6HistoryClipboard;
