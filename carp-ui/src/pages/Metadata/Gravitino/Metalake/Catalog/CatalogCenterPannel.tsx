import React from "react";
import {useAccess, useIntl} from "@umijs/max";
import MetadataGravitinoEditorWeb from "@/pages/Metadata/Gravitino/Metalake/Catalog/Editor";

const MetadataGravitinoMetalakeCatalogCenterPannel: React.FC = () => {
    const intl = useIntl();
    const access = useAccess();

    return (
        <MetadataGravitinoEditorWeb/>
    );
}

export default MetadataGravitinoMetalakeCatalogCenterPannel;
