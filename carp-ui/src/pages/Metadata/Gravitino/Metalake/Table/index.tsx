import {useAccess, useIntl} from "@umijs/max";
import {PageContainer} from "@ant-design/pro-components";

const MetadataGravitinoMetalakeTableWeb: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();

  return (
    <PageContainer title={false}>
      <div>table</div>
    </PageContainer>
  );
}

export default MetadataGravitinoMetalakeTableWeb;
