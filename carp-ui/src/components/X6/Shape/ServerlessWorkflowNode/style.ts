import {createStyles} from 'antd-style';

const useStyles = createStyles(() => {
  return {
    dataProcessingDagNode: {
      display: "flex",
      flexDirection: "row",
      alignItems: "center"
    },
    mainArea: {
      display: "flex",
      flexDirection: "row",
      justifyContent: "space-between",
      padding: "12px",
      width: "180px",
      height: "48px",
      color: "rgba(0, 0, 0, 65%)",
      fontSize: "12px",
      fontFamily: "PingFangSC",
      lineHeight: "24px",
      backgroundColor: "#fff",
      boxShadow: "0 -1px 4px 0 rgba(209, 209, 209, 50%), 1px 1px 4px 0 rgba(217, 217, 217, 50%)",
      borderRadius: "2px",
      border: "1px solid transparent",
      '&:hover': {
        border: "1px solid rgba(0, 0, 0, 10%)",
        boxShadow: "0 -2px 4px 0 rgba(209, 209, 209, 50%), 2px 2px 4px 0 rgba(217, 217, 217, 50%)"
      }
    },

    nodeLogo: {
      display: 'inline-block',
      width: '24px',
      height: '24px',
      backgroundRepeat: 'no-repeat',
      backgroundPosition: 'center',
      backgroundSize: '100%',
    },

    nodeName: {
      overflow: 'hidden',
      display: 'inline-block',
      width: '70px',
      marginLeft: '6px',
      color: 'rgba(0, 0, 0, 65%)',
      fontSize: '12px',
      fontFamily: 'PingFangSC',
      whiteSpace: 'nowrap',
      textOverflow: 'ellipsis',
      verticalAlign: 'top',
    }
  };
});
export default useStyles;
