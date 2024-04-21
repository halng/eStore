export type ProductGroupTableHeader = {
    id: 'name' | 'id' | 'description' | 'createdAt' | 'updatedAt' | 'no'
    label: string
    minWidth?: number
    align?: 'right'
    format?: (value: number) => string
    isDisplay?: boolean
}

export type ProductGroupTableData = {
    no: number
    id: string
    name: string
    description: string
    createdAt: string
    updatedAt: string
}
