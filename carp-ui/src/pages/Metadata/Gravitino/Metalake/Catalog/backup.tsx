import React from "react";
import {useAccess, useIntl} from "@umijs/max";
import {PageContainer} from "@ant-design/pro-components";
import {DraggablePanel, EditorLayout} from '@ant-design/pro-editor';
import MetadataGravitinoMetalakeCatalogLeftPannel from "@/pages/Metadata/Gravitino/Metalake/Catalog/CatalogLeftPannel";
import MetadataGravitinoMetalakeCatalogCenterPannel
    from "@/pages/Metadata/Gravitino/Metalake/Catalog/CatalogCenterPannel";

const MetadataGravitinoMetalakeCatalogWeb: React.FC = () => {
    const intl = useIntl();
    const access = useAccess();

    return (
        <PageContainer title={false}>
            <EditorLayout
                style={{
                    maxWidth: '100%',
                    height: '600px',
                }}
                header={false}
                footer={false}
                bottomPannel={false}
                rightPannel={false}
                leftPannel={{
                    minWidth: '1%',
                    maxWidth: '10%',
                    children: (
                        <DraggablePanel placement="left" maxWidth={'100%'} style={{width: '100%', padding: 12}}>
                            <MetadataGravitinoMetalakeCatalogLeftPannel/>
                        </DraggablePanel>)
                }}
                centerPannel={{
                    children: <MetadataGravitinoMetalakeCatalogCenterPannel/>
                }}
            />
        </PageContainer>
    );
}

export default MetadataGravitinoMetalakeCatalogWeb;
