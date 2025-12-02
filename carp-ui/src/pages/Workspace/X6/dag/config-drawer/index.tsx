import {
  DrawerForm,
  ProFormDependency,
  ProFormDigit,
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
const ConfigDrawer = () => {
  const [form] = Form.useForm();
  const graph = useGraphInstance();
  const updateNode = useGraphStore((state) => state.updateNode);
  const nodes = useGraphStore((state) => state.nodes);
  const [open, setOpen] = useState(false);
  const [nodeData, setNodeData] = useState<NodeData>();

  const onClose = () => {
    setOpen(false);
    form.resetFields();
  };

  const onSave = () => {
    form.validateFields().then(({ label }) => {
      updateNode(nodeData?.id as string, {
        data: {
          ...nodeData,
          label,
        },
      });
      onClose();
    });
  };

  useGraphEvent('node:click', ({ node }) => {
    const { data, id } = node;
    setOpen(true);
    setNodeData({ ...data, id });
    form.setFieldsValue(data);
  });

  useGraphEvent('edge:click', ({ edge }) => {
    console.log('edge', edge)
    console.log('edge source target', edge.source, edge.target)
    const { data, id } = edge;
    // setOpen(true);
    // setEdgeData({ ...data, id });
    // form.setFieldsValue(data);
  });

  useGraphEvent('blank:click', () => {
    onClose();
  });

  return (
    <DrawerForm
      width={300}
      grid={true}
      open={open}
      title={'组件信息'}
      form={form}
      drawerProps={{
        styles: { body: { overflowY: 'scroll' } },
        closeIcon: null,
        mask: false,
        destroyOnClose: true,
        onClose: onClose,
      }}
      onFinish={(values) => {
        onSave();
        return Promise.resolve(true);
      }}
    >
      <ProFormText
        name="label"
        label="组件名"
        rules={[{ required: true, message: '请填写组件名称' }]}
        placeholder={'请填写组件名称'}
      />

      <ProFormGroup label={'Condition'}>
        <ProFormSelect
          name="conditionType"
          label="Condition类型"
          rules={[{ required: true }]}
          allowClear={false}
          options={['class', 'aviator', 'groovy']}
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

      <ProFormGroup label={'Quantifier'}>
        <ProFormDigit name={'min'} label={'起始值'} colProps={{ span: 12 }} initialValue={1} />
        <ProFormDigit name={'max'} label={'结束值'} colProps={{ span: 12 }} />
        <ProFormSwitch name={'more'} label={'More'} colProps={{ span: 8 }} />
        <ProFormSwitch name={'optional'} label={'Optional'} colProps={{ span: 8 }} />
        <ProFormSwitch name={'greedy'} label={'Greedy'} colProps={{ span: 8 }} />
        <ProFormDigit name={'time'} label={'时间'} colProps={{ span: 12 }} />
        <ProFormSelect
          name="timeUnit"
          label="时间单位"
          allowClear={false}
          options={['SECONDS', 'MINUTES', 'HOURS', 'DAYS']}
          colProps={{ span: 12 }}
        />
        <ProFormSelect
          name="consumingStrategy"
          label="连续性"
          allowClear={false}
          options={[
            {value: "STRICT", label: "严格连续"},
            {value: "SKIP_TILL_NEXT", label: "松散连续"},
            {value: "SKIP_TILL_ANY", label: "不确定性连续"},
            {value: "NOT_NEXT", label: "不严格连续"},
            {value: "NOT_FOLLOW", label: "不松散连续"}
          ]}
          initialValue={"SKIP_TILL_NEXT"}
        />
      </ProFormGroup>
    </DrawerForm>
  );
};

export { ConfigDrawer };
