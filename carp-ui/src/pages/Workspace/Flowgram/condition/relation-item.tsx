import { Button } from "@douyinfe/semi-ui";
import './releation-tree.less';

export const RelationItem = ({ data, pos, setElementTerm, onDeleteTerm, onTermChange }) => {

    const handleDeleteTermClick = () => {
        if (typeof onDeleteTerm === 'function') {
            onDeleteTerm(pos, data);
        }
    }

    const handleTermChange = (value) => {
        if (typeof onTermChange === 'function') {
            onTermChange(pos, { ...data, ...value });
        }
    };


    if (typeof setElementTerm !== 'function') {
        console.error('setElementTerm 属性必须设置，且必须是返回 ReactElement 的Function');
        return null;
    }

    return (
        <>
            <div className="vui-relation-item">
                {setElementTerm(data, pos, handleTermChange)}
                <Button onClick={handleDeleteTermClick} className="delete-term">
                    删除
                </Button>
            </div>

        </>
    );
}