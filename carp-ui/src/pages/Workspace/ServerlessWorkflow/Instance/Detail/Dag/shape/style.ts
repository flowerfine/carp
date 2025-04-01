import { createStyles } from 'antd-style';

const useStyles = createStyles(() => {
  return {
    cicdNodeWrap: {
      width: '260px',
      minHeight: '100px',
      backgroundColor: 'white',
      padding: '16px',
      boxSizing: 'border-box',
      borderRadius: '8px',
    },
    stepLabel: {
      overflow: 'hidden',
      color: '#8c8c8c',
      whiteSpace: 'nowrap',
      textOverflow: 'ellipsis',
    },
    cicdNode: {
      marginTop: '10px',
      width: '232px',
      boxSizing: 'border-box',
      border: '1px solid rgba(0, 0, 0, 0.08)',
      borderRadius: '8px',
      backgroundColor: 'white',
    },

    stepBox: {
      width: '100%',
      padding: '12px',
      height: '70px',
      backgroundColor: 'white',
      display: 'flex',
      borderBottom: 'none',
      borderRadius: '8px',
      boxSizing: 'border-box',
      position: 'relative',
    },

    tasks: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      paddingBottom: '10px',
    },
    taskBox: {
      width: '200px',
      padding: '12px',
      height: '70px',
      backgroundColor: 'white',
      display: 'flex',
      border: '1px solid rgba(0, 0, 0, 0.08)',
      borderRadius: '8px',
      boxSizing: 'border-box',
      marginTop: '10px',
    },

    icon: {
      img: { width: '16px', height: '16px', marginTop: '4px' },
    },
    wrap: {
      marginLeft: '8px',
      display: 'flex',
      flexDirection: 'column',
    },
    title: {
      color: '#000',
      fontWeight: '500',
      fontSize: '14px',
      lineHeight: '22px',
      whiteSpace: 'nowrap',
      textOverflow: 'ellipsis',
    },
    desc: {
      marginTop: '8px',
      color: '#00000073',
      fontSize: '10px',
    }
  };
});
export default useStyles;
