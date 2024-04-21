import { ProductGroupTableHeader } from '@/types'

export const HEADER = {
    H_MOBILE: 64,
    H_DESKTOP: 80,
    H_DESKTOP_OFFSET: 80 - 16,
}

export const NAV = {
    WIDTH: 280,
}

export const PRODUCT_GROUP_TABLE_HEADER: ProductGroupTableHeader[] = [
    {
        id: 'no',
        label: 'No.',
        minWidth: 100,
        isDisplay: true,
    },
    {
        id: 'id',
        label: 'ID',
        minWidth: 100,
        isDisplay: false,
    },
    { id: 'name', label: 'Name', minWidth: 170, isDisplay: true },
    { id: 'description', label: 'Description', minWidth: 170, isDisplay: true },
    { id: 'createdAt', label: 'Created At', minWidth: 170, isDisplay: true },
    { id: 'updatedAt', label: 'Updated At', minWidth: 170, isDisplay: true },
]
