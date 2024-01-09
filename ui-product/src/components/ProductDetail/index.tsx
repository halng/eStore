import React from 'react'
import { useParams } from 'react-router-dom'

const ProductDetail = () => {
    const params = useParams()
    return (
        <div className='product-detail'>
            <div className='main'>this is product detail page for {params.slug}</div>
        </div>
    )
}

export default ProductDetail