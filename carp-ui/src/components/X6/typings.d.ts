// @ts-ignore
/* eslint-disable */

declare namespace X6API {

  type Graph = {
    nodes: any[]
    edges: any[]
  }

  type DndGroup = {
    key: string
    label: string
    order: number
    children: DndNode[]
  }

  type DndNode = {
    key: string
    label: string
    order: number
    shape: string
    dndMeta?: DndMeta
    ports?: NodePort[]
  }

  type DndMeta = {
    namespace: string
    label: string
    name: string
    type: string
    icon: string
    version: number
    category: string
    author?: string
    description?: string
    documentation?: string
  }

  type NodePort = {
    id?: string
    group?: string
  }

  interface INodeProperties {
    label: string
    name: string
    type: string
    icon: string
    version: number
    category: string
    author?: string
    description?: string
    documentation?: string
  }

}
