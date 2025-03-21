import { createStyles } from 'antd-style';

const useStyles = createStyles(({css, cx}) => {
  return {

    topicImage: css`
      visibility: hidden;
      cursor: pointer;
    `,

    connectorLine: css`
      display: flex;
      align-items: center;
      padding: 10px;
    `,

    connectorLineSegment: css`
      height: 2px;
      width: 40px;
      background-color: #1890ff;
    `,

    connectorButton: css`
      margin: 0 4px;
    `,

  };
});
export default useStyles;
