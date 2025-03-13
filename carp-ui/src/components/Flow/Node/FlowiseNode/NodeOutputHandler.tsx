import {useRef} from 'react'
import PropTypes from 'prop-types'
import {Flex, Typography} from "antd";

const NodeOutputHandler = ({outputAnchor, data, disabled = false}) => {
  const ref = useRef(null)

  return (
    <div ref={ref}>
      {outputAnchor.type !== 'options' && !outputAnchor.options && (
        <>
          <Flex vertical align={'flex-end'}>
            <Typography.Text>{outputAnchor.label}</Typography.Text>
          </Flex>
        </>
      )}
    </div>
  )
}

NodeOutputHandler.propTypes = {
  outputAnchor: PropTypes.object,
  data: PropTypes.object,
  disabled: PropTypes.bool
}

export default NodeOutputHandler
