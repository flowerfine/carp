
import useStyles from './style';
import {useRef} from "react";

type PipelineCardProps = {
  nodes: any[],
  isEditMode: boolean
}

const PipelineCard = ({nodes, isEditMode}: PipelineCardProps) => {
  const domTree = useRef();
  const {styles} = useStyles();

  return (
    <div>
      <div ref={domTree}>

      </div>
    </div>
  )
}

export default PipelineCard
