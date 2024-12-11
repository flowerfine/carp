import React from "react";
import {useAccess, useIntl} from "@umijs/max";
import {DraggablePanel, EditorLayout} from '@ant-design/pro-editor';

const MetadataGravitinoMetalakeCatalogLeftPannel: React.FC = () => {
    const intl = useIntl();
    const access = useAccess();

    return (
        <div>左侧查询区域，可显示查询 sql 列表</div>
    );
}

export default MetadataGravitinoMetalakeCatalogLeftPannel;
