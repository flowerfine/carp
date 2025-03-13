import React, {useEffect, useRef, useState} from 'react'
import {Flex, Typography} from "antd";
import {useGraphStore} from "@antv/xflow";
import PropTypes from 'prop-types'

// 动态增加节点参数
const NodeInputAnchorHandler: React.FC = ({node, inputAnchor, data}) => {
  const ref = useRef(null)
  const nodes = useGraphStore((state) => state.nodes);
  const [position, setPosition] = useState(0)


  useEffect(() => {
    if (ref.current && ref.current.offsetTop && ref.current.clientHeight) {
      setPosition(ref.current.offsetTop + ref.current.clientHeight / 2)
    }
  }, [data.id, ref])

  useEffect(() => {
    // 可动态增加连接桩，但无法像 Flowise 指定连接桩的位置。Flowise 使用的是 react-flow
  }, []);

  return (
    <div ref={ref}>
      {inputAnchor && (
        <>
          <Flex vertical justify={'flex-start'}>
            <Typography.Text>
              {inputAnchor.label}
              {!inputAnchor.optional && <span style={{color: 'red'}}>&nbsp;*</span>}
            </Typography.Text>
          </Flex>
        </>
      )}
    </div>
  )
}

NodeInputAnchorHandler.propTypes = {
  node: PropTypes.object,
  inputAnchor: PropTypes.object,
  inputParam: PropTypes.object,
  data: PropTypes.object,
  disabled: PropTypes.bool,
  isAdditionalParams: PropTypes.bool,
  disablePadding: PropTypes.bool,
  onHideNodeInfoDialog: PropTypes.func
}

export default NodeInputAnchorHandler
