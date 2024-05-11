export type ProductGroupTableHeader = {
    id: 'name' | 'id' | 'description' | 'updatedDate' | 'no' | 'status'
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
    updatedDate: string
    status: 'ENABLED' | 'DISABLED'
}
