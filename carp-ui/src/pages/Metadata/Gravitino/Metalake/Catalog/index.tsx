import {useAccess, useIntl} from "@umijs/max";
import {PageContainer} from "@ant-design/pro-components";

const MetadataGravitinoMetalakeCatalogWeb: React.FC = () => {
  const intl = useIntl();
  const access = useAccess();

  return (
    <PageContainer title={false}>
      <div>catalog</div>
    </PageContainer>
  );
}

export default MetadataGravitinoMetalakeCatalogWeb;
