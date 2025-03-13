import {useRef, useState} from 'react'
import {Flex, Typography} from "antd";
import PropTypes from 'prop-types'

const NodeInputHandler = ({
                            inputAnchor,
                            inputParam,
                            data,
                            disabled = false,
                            isAdditionalParams = false,
                            disablePadding = false,
                            onHideNodeInfoDialog
                          }) => {

  const ref = useRef(null)

  const [position, setPosition] = useState(0)

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

      {((inputParam && !inputParam.additionalParams) || isAdditionalParams) && (
        <>
          <Flex vertical justify={'flex-start'} >
            <div style={{ display: 'flex', flexDirection: 'row' }}>
              <Typography.Text>
                {inputParam.label}
                {!inputParam.optional && <span style={{ color: 'red' }}>&nbsp;*</span>}
              </Typography.Text>
              <div style={{ flexGrow: 1 }}></div>
            </div>
          </Flex>
        </>
      )}
    </div>
  )
}

NodeInputHandler.propTypes = {
  inputAnchor: PropTypes.object,
  inputParam: PropTypes.object,
  data: PropTypes.object,
  disabled: PropTypes.bool,
  isAdditionalParams: PropTypes.bool,
  disablePadding: PropTypes.bool,
  onHideNodeInfoDialog: PropTypes.func
}

export default NodeInputHandler
