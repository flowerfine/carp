import { createStyles } from 'antd-style';

export const useStyles = createStyles(({ css, cx, prefixCls }) => ({
  nodeWrap: cx(
    `${prefixCls}-xx`,
    css`
      display: flex;
      z-index: 10 !important;
      position: absolute;
      border-radius: 8px;
      padding: 16px 12px;
      box-sizing: border-box;
      flex: 1;
      background: #ffffff;
      border: 1px solid rgba(255, 255, 255, 0.04);
      box-shadow: 0 4px 6px -2px rgba(25, 15, 15, 0.05), 0 0 1px 0 rgba(0, 0, 0, 0.08);
    `,
  ),
}));
