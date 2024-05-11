import { ProductGroupTableData } from '@types'

export const mockProductGroupData: ProductGroupTableData[] = Array.from({ length: 100 }, (_, index) => ({
    no: index + 1,
    id: `product-group-${index + 1}`,
    name: `Product Group ${index + 1}`,
    description: `Description for Product ${index + 1}`,
    updatedDate: new Date().toUTCString(),
    status: index % 5 === 0 ? 'ENABLED' : 'DISABLED',
}))
