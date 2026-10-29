import {
  DrawerForm,
  ProFormDigit,
  ProFormSelect,
} from '@ant-design/pro-components';
import { useGraphEvent, useGraphInstance, useGraphStore } from '@antv/xflow';
import { Form } from 'antd';
import { useState } from 'react';

interface EdgeData {
  id: string;
  label?: string;
  status?: 'default' | 'running' | 'success' | 'failed';
  source: any;
  target: any;
}

const EdgeDrawer = () => {
  const [form] = Form.useForm();
  const graph = useGraphInstance();
  const updateEdge = useGraphStore((state) => state.updateEdge);
  const [open, setOpen] = useState(false);
  const [edgeData, setEdgeData] = useState<EdgeData>();

  const onClose = () => {
    setOpen(false);
    form.resetFields();
  };

  useGraphEvent('edge:click', ({ edge }) => {
    const { data, id, source, target } = edge;
    setOpen(true);
    setEdgeData({ ...data, id, source, target });
    form.setFieldsValue(data);
  });

  useGraphEvent('blank:click', () => {
    onClose();
  });

  return (
    <>
      {open && (
        <DrawerForm
          width={300}
          grid={true}
          open={open}
          title={'边信息'}
          form={form}
          drawerProps={{
            styles: { body: { overflowY: 'scroll' } },
            closeIcon: null,
            mask: false,
            destroyOnClose: true,
            onClose: onClose,
          }}
          onFinish={(values) => {
            updateEdge(edgeData?.id as string, {
              data: {
                ...edgeData,
                ...values,
              },
            });
            onClose();
            return Promise.resolve(true);
          }}
        >
          <ProFormDigit name={'time'} label={'时间'} colProps={{ span: 12 }} />
          <ProFormSelect
            name="timeUnit"
            label="时间单位"
            allowClear={false}
            options={['MILLISECONDS', 'SECONDS', 'MINUTES', 'HOURS', 'DAYS']}
            colProps={{ span: 12 }}
          />
          <ProFormSelect
            name="consumingStrategy"
            label="连续性"
            allowClear={false}
            options={[
              { value: 'STRICT', label: '严格连续' },
              { value: 'SKIP_TILL_NEXT', label: '松散连续' },
              { value: 'SKIP_TILL_ANY', label: '不确定性连续' },
              { value: 'NOT_NEXT', label: '不严格连续' },
              { value: 'NOT_FOLLOW', label: '不松散连续' },
            ]}
            initialValue={'SKIP_TILL_NEXT'}
          />
        </DrawerForm>
      )}
    </>
  );
};

export { EdgeDrawer };
