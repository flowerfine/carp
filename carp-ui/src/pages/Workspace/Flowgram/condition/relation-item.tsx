import { Button, IconButton } from "@douyinfe/semi-ui";
import { IconDelete, IconMinusCircle, IconPlusCircle } from "@douyinfe/semi-icons";
import styles from './relation-tree.less';

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
            <div className={styles.vuiRelationItem}>
                {setElementTerm(data, pos, handleTermChange)}

                <div className={styles.buttonGroup}>
                    <IconButton
                        theme="borderless"
                        icon={<IconDelete />}
                        size="small"
                        onClick={handleDeleteTermClick}
                    />
                </div>
            </div>
        </>
    );
}
