'use client'
import React, { useEffect, useState } from 'react'
import { TableView, ActionHeader } from '@components'
import { PRODUCT_GROUP_TABLE_HEADER } from '@constants'
import { ProductGroupTableData } from '@types'
import { mockProductGroupData } from '../../../__mock__'

const ProductGroups: React.FC = () => {
    const [data, setData] = useState<ProductGroupTableData[]>([])
    useEffect(() => {
        setData(mockProductGroupData)
    }, [])
    return (
        <div>
            {/* <h1>Product Group!</h1> */}
            <ActionHeader tableData={data} />
            <TableView tableData={data} tableHeader={PRODUCT_GROUP_TABLE_HEADER} />
        </div>
    )
}

export default ProductGroups
