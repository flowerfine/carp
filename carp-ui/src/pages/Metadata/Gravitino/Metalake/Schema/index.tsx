import {useAccess, useIntl} from "@umijs/max";
import {PageContainer} from "@ant-design/pro-components";

const MetadataGravitinoMetalakeSchemaWeb: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();

  return (
    <PageContainer title={false}>
      <div>schema</div>
    </PageContainer>
  );
}

export default MetadataGravitinoMetalakeSchemaWeb;
