import { useEffect, useState } from "react";
import { Card, Skeleton, Spin, Tree } from "antd";
import { useIntl } from "@umijs/max";
import { DeptService } from "@/services/admin/security/dept.service";
import { AdminSecurityAPI } from "@/services/admin/security/typings";

const AdminSecurityUserLeftDeptWeb: React.FC = (props: {
  onSelect: (data: AdminSecurityAPI.SecDept) => void;
}) => {
  const intl = useIntl();
  const { onSelect } = props;
  const [dataLoading, setDataLoading] = useState<boolean>(false);
  const [initLoading, setInitLoading] = useState<boolean>(false);
  const [deptData, setDeptData] = useState<AdminSecurityAPI.SecDept[] | any>([]);

  useEffect(() => {
    setInitLoading(true);
    DeptService.page({}).then(response => {
      setInitLoading(false)
      if (response.data) {
        setDeptData(response.data)
        onSelect(response.data[0])
      }
    })
  }, []);

  return (
    <div>
      <Card
        style={{ height: 'calc(100vh - 200px)', overflow: 'auto' }}
        bordered={false}
      >
        <Skeleton loading={initLoading} paragraph={{ rows: 5 }}>
          <Spin spinning={dataLoading}>
            <Tree
              blockNode={true}
              fieldNames={{ key: 'id', title: 'name' }}
              treeData={deptData}
              onSelect={(keys_, { node }) => {
                onSelect({ ...node });
              }}
            >
            </Tree>
          </Spin>
        </Skeleton>

      </Card>
    </div>
  );
}

export default AdminSecurityUserLeftDeptWeb;
