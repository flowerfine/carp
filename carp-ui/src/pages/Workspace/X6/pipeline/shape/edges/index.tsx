import React from "react";
import {isArray, isEmpty} from 'lodash'
import classnames from "classnames";
import useStyles from './style';

const STROKEWIDTH = 2
const EDGES_LENGTH = 35
const POINT_R = 6
const CURVE_R = 10

type PipelineEdgesProps = {
  heights: number[],
  index: number,
  position?: number,
  isEditMode: boolean,
  onInsertColumn: (index: number) => void
}

const PipelineEdges = ({heights, index, position, isEditMode, onInsertColumn}: PipelineEdgesProps) => {
  const {styles} = useStyles();

  const getMaxHeight = () => {
    return Math.max(
      ...[...(heights[index] || []), ...(heights[index + 1] || [])]
    )
  }

  const getRightLinePath = (height) => {
    if (!height) {
      return `M ${EDGES_LENGTH} ${POINT_R} H ${2 * EDGES_LENGTH - POINT_R}`
    }
    height += POINT_R - 10
    return `M${EDGES_LENGTH} ${POINT_R}  V ${height -
    CURVE_R} Q ${EDGES_LENGTH}, ${height} ${EDGES_LENGTH +
    CURVE_R}, ${height} H ${2 * EDGES_LENGTH - POINT_R}`
  }

  const getLeftLinePath = (height) => {
    if (!height) {
      return `M${POINT_R} ${POINT_R} H ${EDGES_LENGTH}`
    }
    height += POINT_R - 10
    return `M${POINT_R} ${height} H ${EDGES_LENGTH -
    CURVE_R} Q ${EDGES_LENGTH},${height} ${EDGES_LENGTH},${height -
    CURVE_R} V ${POINT_R}`
  }


  const renderEditButton = () => {
    if (!isEditMode) {
      return
    }
    return (
      <g className={classnames(styles.editButton)}>
        <circle
          cx={EDGES_LENGTH}
          cy={POINT_R}
          r="10"
          stroke="#d8dee5"
          strokeDasharray="3,3"
          fill="white"
          strokeWidth={STROKEWIDTH}
          onClick={onInsertColumn(index + 1)}
        />
        <line
          x1={EDGES_LENGTH - 5}
          x2={EDGES_LENGTH + 5}
          y1={POINT_R}
          y2={POINT_R}
          stroke="black"
        />
        <line
          x1={EDGES_LENGTH}
          x2={EDGES_LENGTH}
          y1={POINT_R - 5}
          y2={POINT_R + 5}
          stroke="black"
        />
      </g>
    )
  }

  const renderLine = (height, posision, isShallow) => {
    if (posision === 'left') {
      return (
        <path
          key={`left-${index}`}
          d={getLeftLinePath(height)}
          stroke={isShallow ? '#d8dee5' : 'black'}
          strokeWidth={STROKEWIDTH}
          fill="none"
          markerStart={`url(#path-marker-${isShallow ? 'grey' : 'black'})`}
          markerEnd={`url(#path-marker-${isShallow ? 'grey' : 'black'})`}
        />
      )
    }
    return (
      <path
        key={`right-${index}`}
        d={getRightLinePath(height)}
        stroke={isShallow ? '#d8dee5' : 'black'}
        strokeWidth={STROKEWIDTH}
        fill="none"
        markerStart={`url(#path-marker-${isShallow ? 'grey' : 'black'})`}
        markerEnd={`url(#path-marker-${isShallow ? 'grey' : 'black'})`}
      />
    )
  }

  const renderLines = (heights, posision) => {
    if (!isArray(heights)) {
      return null
    }

    return heights.map((height, index) => {
      if (index === heights.length - 1) {
        return null
      }
      return (
        <React.Fragment key={index}>
          {renderLine(height, posision)}
        </React.Fragment>
      )
    })
  }

  const renderEditLines = () => {
    const lineLists = []

    if (!isArray(heights) || !isEditMode) {
      return null
    }

    if (isArray(heights[index]) && !isEmpty(heights[index])) {
      const {length, last = length - 1} = heights[index]
      lineLists.push(renderLine(heights[index][last] - 50, 'left', true))
    }

    if (isArray(heights[index + 1]) && !isEmpty(heights[index + 1])) {
      const {length, last = length - 1} = heights[index + 1]
      lineLists.push(renderLine(heights[index + 1][last] - 50, 'right', true))
    }

    return lineLists
  }

  const renderFirstLines = () => {
    return (
      <>
        {renderLine(0, 'right')}
        {renderLine(0, 'left')}
      </>
    )
  }

  const renderBoldLines = () => {
    return (
      <>
        {renderLines(heights[index], 'left')}
        {renderLines(heights[index + 1], 'right')}
      </>
    )
  }

  const renderEdges = () => {
    return (
      <svg xmlns="http://www.w3.org/2000/svg">
        <defs>
          <marker
            id={'path-marker-black'}
            viewBox="0 0 12 12"
            refX="6"
            refY="6"
            markerWidth="6"
            markerHeight="6"
            orient="auto-start-reverse"
          >
            <circle
              cx={POINT_R}
              cy={POINT_R}
              r={POINT_R - 1}
              fill={'#329dce'}
            />
          </marker>
          <marker
            id={'path-marker-grey'}
            viewBox="0 0 12 12"
            refX="6"
            refY="6"
            markerWidth="6"
            markerHeight="6"
            orient="auto-start-reverse"
          >
            <circle
              cx={POINT_R}
              cy={POINT_R}
              r={POINT_R - 1}
              fill={'#d8dee5'}
            />
          </marker>
        </defs>
        {renderEditLines()}
        {renderBoldLines()}
        {renderFirstLines()}
        {renderEditButton()}
      </svg>
    )
  }

  return (
    <>
      {(!isEditMode && index === heights.length - 1)
        ? (<div className={classnames(styles.connectors__column)}/>)
        : (
          <div
            className={classnames(styles.connectors__column)}
            style={{height: `${getMaxHeight() + 32}px`}}
          >
            {renderEdges()}
            {isEditMode && !index && posision === 'Right'
              ? renderEditButton()
              : null}
          </div>)
      }
    </>
  );
}

export default PipelineEdges;
