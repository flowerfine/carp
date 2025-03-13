declare namespace FlowiseNodeAPI {

  type NodeParamsType =
    | 'string'
    | 'number'
    | 'boolean'
    | 'date'
    | 'json'
    | 'options'
    | 'multiOptions'
    | 'asyncOptions'
    | 'datagrid'
    | 'code'
    | 'password'
    | 'file'
    | 'folder'
    | 'tabs'

  type CommonType = string | number | boolean | undefined | null

  interface ICommonObject {
    [key: string]: any | CommonType | ICommonObject | CommonType[] | ICommonObject[]
  }

  interface IVariable {
    name: string
    value: string
    type: string
  }

  interface IAttachment {
    content: string
    contentType: string
    size?: number
    filename?: string
  }

  interface INodeOptionsValue {
    label: string
    name: string
    description?: string
  }

  interface INodeDisplay {
    [key: string]: string[] | string
  }

  interface INodeCredential {
    label: string
    name: string
    description?: string
    inputs?: INodeParams[]
  }

  interface INodeExecutionData {
    [key: string]: CommonType | CommonType[] | ICommonObject | ICommonObject[]
  }

  export interface INodeOutputsValue {
    label: string
    name: string
    description?: string
    hidden?: boolean
    isAnchor?: boolean
  }

  export interface INodeParams {
    label: string
    name: string
    type: NodeParamsType | string
    default?: CommonType | ICommonObject | ICommonObject[]
    description?: string
    warning?: string
    options?: Array<INodeOptionsValue>
    datagrid?: Array<ICommonObject>
    credentialNames?: Array<string>
    optional?: boolean | INodeDisplay
    step?: number
    rows?: number
    list?: boolean
    acceptVariable?: boolean
    placeholder?: string
    fileType?: string
    additionalParams?: boolean
    loadMethod?: string
    hidden?: boolean
    hideCodeExecute?: boolean
    codeExample?: string
    hint?: Record<string, string>
    tabIdentifier?: string
    tabs?: Array<INodeParams>
    refresh?: boolean
    freeSolo?: boolean
    loadPreviousNodes?: boolean
  }

  interface INodeProperties {
    label: string
    name: string
    type: string
    icon: string
    version: number
    category: string // TODO: use enum instead of string
    tags?: string[]
    description?: string
    filePath?: string
    badge?: string
    deprecateMessage?: string
    hideOutput?: boolean
    author?: string
    documentation?: string
  }

  interface INodeData extends INodeProperties {
    id: string
    inputs?: ICommonObject
    outputs?: ICommonObject
    credential?: string
    instance?: any
    loadMethod?: string // method to load async options
  }

  interface INode extends INodeProperties {
    inputs?: INodeParams[]
    output?: INodeOutputsValue[]
    loadMethods?: {
      [key: string]: (nodeData: INodeData, options?: ICommonObject) => Promise<INodeOptionsValue[]>
    }
    init?(nodeData: INodeData, input: string, options?: ICommonObject): Promise<any>
    run?(nodeData: INodeData, input: string, options?: ICommonObject): Promise<string | ICommonObject>
  }

}
