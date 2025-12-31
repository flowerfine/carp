import { RelationTree } from './relation-tree';

const field = {
    value: {
        ops: 'and',
        children: [
            { type: 'term', key: 'Key1', op: '>', value: 0 },
            {
                type: 'group',
                ops: 'or',
                children: [
                    { type: 'term', key: 'Key2', op: '<', value: 20 },
                    { type: 'term', key: 'Key3', op: '>', value: 10 },
                ],
            },
        ],
    },
    onChange: (value) => { }
};

const Rule = () => {

    return (
        <>
            <RelationTree value={{ ...field.value }}
                onChange={(value) => field.onChange(value)}
                readonly={false} />
        </>
    );
}

export default Rule;
