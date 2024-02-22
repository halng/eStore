import { ProductAPI } from 'api-estore-v2'
import React, { useEffect } from 'react'
import { useParams } from 'react-router-dom'

const ProductDetail = () => {
    // get data form product api base on slug
    const params = useParams()
    useEffect(() => {
        if (params.slug) {
            ProductAPI.getProductBySlug(params.slug)
                .then((res) => console.log(res))
                .catch((err) => console.log(err))
        }
    }, [params])
    return (
        <div className='product-detail'>
            <div className='main'>this is product detail page for {params.slug}</div>
        </div>
    )
}

export default ProductDetail
