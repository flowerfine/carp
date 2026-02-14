import { useEffect, useLayoutEffect, useRef, useState } from "react";
import { GridContent, PageContainer } from "@ant-design/pro-components"
import { useIntl } from "@umijs/max";
import useStyles from './style.style';
import { Menu } from "antd";
import { CepFunctionCategoryService } from "@/services/workspace/cep/cep-function-category.service";
import FunctionListWeb from "./component/FunctionList";

type SettingsState = {
  namespace: string;
  selectKey: string;
};

export default () => {
  const intl = useIntl();
  const { styles } = useStyles();

  const [initConfig, setInitConfig] = useState<SettingsState>({
    namespace: 'default',
    selectKey: ''
  });

  const [menuData, setMenuData] = useState<Array<any>>([]);

  useEffect(() => {
    CepFunctionCategoryService.listAll(initConfig.namespace).then((res) => {
      if (res.data) {
        setMenuData(res.data.map((item: any) => ({ key: item.id, label: item.name })));
      }
    });
  }, []);


  const renderChildren = () => {
    const { selectKey } = initConfig;
    if (selectKey) {
      return <FunctionListWeb namespace={initConfig.namespace} categoryId={parseInt(selectKey)} />;
    }
    return <></>
  };

  return (
    <PageContainer content={intl.formatMessage({ id: 'menu.workspace.cep.function.desc' })}>
      <GridContent>
        <div
          className={styles.main}
        >
          <div className={styles.leftMenu}>
            <Menu
              mode={'inline'}
              selectedKeys={[initConfig.selectKey]}
              onClick={({ key }) => {
                setInitConfig({
                  ...initConfig,
                  selectKey: key
                });
              }}
              items={menuData}
            />
          </div>
          <div className={styles.right}>
            {renderChildren()}
          </div>
        </div>
      </GridContent>
    </PageContainer>
  )
}
