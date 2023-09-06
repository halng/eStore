import React, { useState, useEffect } from 'react'
import { ProductAttributeValueType } from '../../../../types/ProductAttributeType'
import { CommonType } from '../../../../types/CommonType'
import { ProductAttributeAPI } from 'api-estore-v2'
import { toast } from 'react-toastify'
const ProductAttribute = () => {
    const [attributeValues, setAttributeValues] = useState<ProductAttributeValueType[]>([])
    const [attributes, setAttributes] = useState<CommonType[]>([])

    useEffect(() => {
        ProductAttributeAPI.getAttribute()
            .then((res) => {
                setAttributes(res.data)
            })
            .catch(() => {
                toast.error('Cannot get all attribute')
            })
    }, [])

    const onAddAttributeHandler = () => {
        if (attributes.length === attributeValues.length) {
            toast.warn('Cannot add more than number of attribute exist!')
        } else {
            const oldAttribute = [...attributeValues]
            const newAttribute: ProductAttributeValueType = { id: '', name: '', value: '' }
            oldAttribute.push(newAttribute)
            setAttributeValues([...oldAttribute])
        }
    }

    const onAttributeChange = (
        e: React.ChangeEvent<HTMLSelectElement> | React.ChangeEvent<HTMLInputElement>,
        oldId: string,
    ) => {
        const oldAttribute = [...attributeValues]
        const index = oldAttribute.findIndex((obj) => obj.id === oldId)

        oldAttribute[index].id = e.target.value

        setAttributeValues([...oldAttribute])
    }

    const onAttributeValueChange = (e: any, attributeId: string) => {
        const oldAttribute = [...attributeValues]
        const index = oldAttribute.findIndex((obj) => obj.id === attributeId)

        oldAttribute[index].value = e.target.value

        setAttributeValues([...oldAttribute])
    }

    const onRemoveAttribute = (attId: string) => {
        const oldAttribute = [...attributeValues]
        const newAttribute = oldAttribute.filter((obj) => obj.id !== attId)
        setAttributeValues([...newAttribute])
    }

    return (
        <div className='product-attribute w-75'>
            {attributeValues.map((item) => (
                <div key={item.id} className='d-flex align-items-center justify-content-evenly pt-3'>
                    <select
                        className='form-control me-3'
                        id='selectGroup'
                        value={item.id}
                        onChange={(e) => onAttributeChange(e, item.id)}
                    >
                        <option value='' hidden>
                            Choose Attribute
                        </option>
                        {(attributes || []).map((att) => (
                            <option value={att.id} key={att.id}>
                                {att.name}
                            </option>
                        ))}
                    </select>
                    <input
                        type='text'
                        placeholder='Enter value'
                        className='form-control me-3'
                        onChange={(e) => onAttributeValueChange(e, item.id)}
                    />
                    <button
                        type='button'
                        className='btn btn-outline-danger '
                        onClick={() => onRemoveAttribute(item.id)}
                    >
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='16'
                            height='16'
                            fill='currentColor'
                            className='bi bi-x-lg'
                            viewBox='0 0 16 16'
                        >
                            <path d='M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8 2.146 2.854Z' />
                        </svg>
                    </button>
                </div>
            ))}
            <div className='pt-3'>
                <button type='button' className='btn btn-info' onClick={onAddAttributeHandler}>
                    Add
                    <svg
                        xmlns='http://www.w3.org/2000/svg'
                        width='16'
                        height='16'
                        fill='currentColor'
                        className='bi bi-plus'
                        viewBox='0 0 16 16'
                    >
                        <path d='M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z' />
                    </svg>
                </button>
            </div>
        </div>
    )
}

export default ProductAttribute
