import {
  DrawerForm,
  ProFormDependency,
  ProFormDigitRange,
  ProFormGroup,
  ProFormSelect,
  ProFormSwitch,
  ProFormText,
} from '@ant-design/pro-components';
import { useGraphEvent, useGraphInstance, useGraphStore } from '@antv/xflow';
import { Form } from 'antd';
import { useState } from 'react';

interface NodeData {
  id: string;
  label?: string;
  status?: 'default' | 'running' | 'success' | 'failed';
}

const NodeDrawer = () => {
  const [form] = Form.useForm();
  const graph = useGraphInstance();
  const updateNode = useGraphStore((state) => state.updateNode);
  const [open, setOpen] = useState(false);
  const [nodeData, setNodeData] = useState<NodeData>();

  const onClose = () => {
    setOpen(false);
    form.resetFields();
  };

  useGraphEvent('node:click', ({ node }) => {
    const { data, id } = node;
    setOpen(true);
    setNodeData({ id: id, label: data?.name });
    console.log('nodeData', data);
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
          title={'节点信息'}
          form={form}
          initialValues={nodeData}
          drawerProps={{
            styles: { body: { overflowY: 'scroll' } },
            closeIcon: null,
            mask: false,
            destroyOnClose: true,
            onClose: onClose,
          }}
          onFinish={(values) => {
            updateNode(nodeData?.id as string, {
              data: {
                ...nodeData,
                ...values,
              },
            });
            onClose();
            return Promise.resolve(true);
          }}
        >
          <ProFormText
            name="label"
            label="节点名"
            rules={[{ required: true, message: '请填写节点名称' }]}
            placeholder={'请填写节点名称'}
            allowClear={false}
            readonly
          />
          <ProFormGroup label={'过滤条件'}>
            <ProFormSelect
              name="conditionType"
              label="条件类型"
              rules={[{ required: true }]}
              allowClear={false}
              options={['aviator', 'groovy', 'class']}
            />

            <ProFormDependency name={['conditionType']}>
              {({ conditionType }) => {
                if (conditionType == 'class') {
                  return (
                    <ProFormGroup>
                      <ProFormText
                        name="className"
                        label="类名"
                        rules={[{ required: true }]}
                        placeholder={'请填写Condition类名'}
                      />
                    </ProFormGroup>
                  );
                } else if (conditionType == 'aviator') {
                  return (
                    <ProFormGroup>
                      <ProFormText
                        name={'className'}
                        label={'类名'}
                        hidden
                        rules={[{ required: true }]}
                        initialValue={'xxxxx'}
                      />
                      <ProFormText
                        name={'expression'}
                        label={'表达式'}
                        rules={[{ required: true }]}
                        placeholder={'请填写 Aviator 表达式'}
                      />
                    </ProFormGroup>
                  );
                } else if (conditionType == 'groovy') {
                  return (
                    <ProFormGroup>
                      <ProFormText
                        name={'className'}
                        label={'类名'}
                        hidden
                        rules={[{ required: true }]}
                        initialValue={'xxxxx'}
                      />
                      <ProFormText
                        name={'expression'}
                        label={'表达式'}
                        rules={[{ required: true }]}
                        placeholder={'请填写 Groovy 表达式'}
                      />
                    </ProFormGroup>
                  );
                }
                return <ProFormGroup />;
              }}
            </ProFormDependency>
          </ProFormGroup>

          <ProFormGroup label={'数量限制'}>
            <ProFormDigitRange name={'minMax'} label={'数量范围'} separator="-" separatorWidth={60} placeholder={['最小值', '最大值']} />
            <ProFormSwitch name={'more'} label={'More'} colProps={{ span: 8 }} />
            <ProFormSwitch name={'optional'} label={'Optional'} colProps={{ span: 8 }} />
            <ProFormSwitch name={'greedy'} label={'Greedy'} colProps={{ span: 8 }} />
          </ProFormGroup>
        </DrawerForm>
      )}
    </>
  );
};

export { NodeDrawer };
