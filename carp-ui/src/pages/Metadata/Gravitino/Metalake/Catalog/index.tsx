import React from "react";
import {useAccess, useIntl} from "@umijs/max";
import {PageContainer} from "@ant-design/pro-components";
import MetadataGravitinoEditorWeb from "@/pages/Metadata/Gravitino/Metalake/Catalog/Editor";

const MetadataGravitinoMetalakeCatalogWeb: React.FC = () => {
    const intl = useIntl();
    const access = useAccess();

    return (
        <PageContainer title={false}>
            <MetadataGravitinoEditorWeb/>
        </PageContainer>
    );
}

export default MetadataGravitinoMetalakeCatalogWeb;
