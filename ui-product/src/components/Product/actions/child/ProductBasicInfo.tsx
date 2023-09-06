import React, { useState, useEffect } from 'react'
import { CommonType } from '../../../../types/CommonType'
import { ProductGroupAPI } from 'api-estore-v2'
import { toast } from 'react-toastify'

const ProductBasicInfo = () => {
    const [groups, setGroups] = useState<CommonType[]>([])

    useEffect(() => {
        ProductGroupAPI.getGroup()
            .then((res) => {
                setGroups(res.data)
            })
            .catch(() => {
                toast.error('Cannot get all group')
            })
    }, [])

    return (
        <div className='product-attribute w-75'>
            <div className='row mb-3'>
                <label htmlFor='inputProductName' className='col-sm-2 col-form-label'>
                    Name
                </label>
                <div className='col-sm-10'>
                    <input type='text' className='form-control' id='inputProductName' />
                </div>
            </div>
            <div className='row mb-3'>
                <label htmlFor='inputProductSlug' className='col-sm-2 col-form-label'>
                    Slug
                </label>
                <div className='col-sm-10'>
                    <input type='text' className='form-control' id='inputProductSlug' />
                </div>
            </div>
            <div className='row mb-3'>
                <label htmlFor='inputPrice' className='col-sm-2 col-form-label'>
                    Price
                </label>
                <div className='col-sm-10'>
                    <input type='number' className='form-control' id='inputPrice' />
                </div>
            </div>
            <div className='row mb-3'>
                <label htmlFor='inputQuantity' className='col-sm-2 col-form-label'>
                    Quantity
                </label>
                <div className='col-sm-10'>
                    <input type='number' className='form-control' id='inputQuantity' />
                </div>
            </div>
            <div className='row mb-3'>
                <label className='col-sm-2 col-form-label' htmlFor='selectGroup'>
                    Group
                </label>
                <div className='col-sm-10'>
                    <select className='form-control' id='selectGroup'>
                        <option selected hidden>
                            Choose group...
                        </option>
                        {(groups || []).map((item) => (
                            <option value={item.id} key={item.id}>
                                {item.name}
                            </option>
                        ))}
                    </select>
                </div>
            </div>
            <div className='row mb-3'>
                <label htmlFor='inputDescription' className='col-sm-2 col-form-label'>
                    Description
                </label>
                <div className='col-sm-10'>
                    <textarea rows={5} className='form-control' id='inputDescription' />
                </div>
            </div>
        </div>
    )
}

export default ProductBasicInfo
