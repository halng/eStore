import React from 'react'

const ProductSEO = () => {
    return (
        <div className='product-seo w-75'>
            <div className='row mb-3'>
                <label htmlFor='inputKeywordSEO' className='col-sm-2 col-form-label'>
                    Keyword
                </label>
                <div className='col-sm-10'>
                    <textarea rows={5} className='form-control' id='inputKeywordSEO' />
                </div>
            </div>
            <div className='row mb-3'>
                <label htmlFor='inputMateDataSEO' className='col-sm-2 col-form-label'>
                    Meta Data
                </label>
                <div className='col-sm-10'>
                    <textarea rows={5} className='form-control' id='inputMateDataSEO' />
                </div>
            </div>
        </div>
    )
}

export default ProductSEO
