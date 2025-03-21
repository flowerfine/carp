import { createStyles } from 'antd-style';

const useStyles = createStyles(({css, cx}) => {
  return {
    connectors__column: css`
      vertical-align: top;
      // width: 35px;
      display: inline-block;
      padding-top: 80px;

      svg {
        height: 100%;
        width: 70px;
        overflow: visible;
      }
    `,

    editButtion: css`
      &:hover {
        circle {
          cursor: pointer;
          stroke: #329dce;
        }
      }
    `,
  };
});
export default useStyles;
