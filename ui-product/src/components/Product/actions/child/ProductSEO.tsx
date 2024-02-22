import React from 'react'

const ProductSEO = ({ setFunc, getFunc, isDisable }: any) => {
    return (
        <div className='product-seo w-75'>
            <div className='row mb-3'>
                <label htmlFor='inputKeywordSEO' className='col-sm-2 col-form-label'>
                    Keyword
                </label>
                <div className='col-sm-10'>
                    <textarea
                        rows={5}
                        className='form-control'
                        id='inputKeywordSEO'
                        value={getFunc('seo.keyword')}
                        onChange={(e) => setFunc('seo.keyword', e)}
                        disabled={isDisable}
                    />
                </div>
            </div>
            <div className='row mb-3'>
                <label htmlFor='inputMateDataSEO' className='col-sm-2 col-form-label'>
                    Meta Data
                </label>
                <div className='col-sm-10'>
                    <textarea
                        rows={5}
                        className='form-control'
                        id='inputMateDataSEO'
                        value={getFunc('seo.metadata')}
                        onChange={(e) => setFunc('seo.metadata', e)}
                        disabled={isDisable}
                    />
                </div>
            </div>
        </div>
    )
}

export default ProductSEO
