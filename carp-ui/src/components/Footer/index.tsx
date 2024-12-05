import React from 'react';
import {GithubOutlined} from '@ant-design/icons';
import {DefaultFooter} from '@ant-design/pro-components';
import Settings from "../../../config/defaultSettings";

const Footer: React.FC = () => {
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={"powerd by " + Settings.title}
      links={[
        {
          key: 'github',
          title: <GithubOutlined/>,
          href: 'https://github.com/flowerfine/scaleph',
          blankTarget: true,
        },
        {
          key: 'Scaleph',
          title: 'Scaleph',
          href: 'https://flowerfine.github.io/scaleph-repress-site/',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
