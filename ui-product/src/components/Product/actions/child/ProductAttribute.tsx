import React, { useState, useEffect } from 'react'
import { ProductAttributeValueType } from '../../../../types/ProductAttributeType'
import { CommonType } from '../../../../types/CommonType'
import { ProductAttributeAPI } from 'api-estore-v2'
import { toast } from 'react-toastify'
import { UseFormSetValue, UseFormGetValues } from 'react-hook-form'
import { ProductCreateType } from '../../../../types/ProductType'
interface props {
    setFunc: UseFormSetValue<ProductCreateType>
    getFunc: UseFormGetValues<ProductCreateType>
    isDisable: boolean
}
const ProductAttribute = ({ setFunc, getFunc, isDisable }: props) => {
    const [attributeValues, setAttributeValues] = useState<ProductAttributeValueType[]>([])
    const [attributes, setAttributes] = useState<CommonType[]>([])

    useEffect(() => {
        const data = getFunc('attributes') || []
        setAttributeValues(data)
        ProductAttributeAPI.getAttribute()
            .then((res) => {
                setAttributes(res.data)
            })
            .catch(() => {
                toast.error('Cannot get all attribute')
            })
    }, [getFunc])

    const onAddAttributeHandler = () => {
        if (attributes.length === attributeValues.length) {
            toast.warn('Cannot add more than number of attribute exist!')
        } else {
            const oldAttribute = [...attributeValues]
            const newAttribute: ProductAttributeValueType = { id: '', name: '', value: '' }
            oldAttribute.push(newAttribute)
            setAttributeValues([...oldAttribute])
            setFunc('attributes', [...oldAttribute])
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
        setFunc('attributes', [...oldAttribute])
    }

    const onAttributeValueChange = (e: any, attributeId: string) => {
        const oldAttribute = [...attributeValues]
        const index = oldAttribute.findIndex((obj) => obj.id === attributeId)

        oldAttribute[index].value = e.target.value

        setAttributeValues([...oldAttribute])
        setFunc('attributes', [...oldAttribute])
    }

    const onRemoveAttribute = (attId: string) => {
        const oldAttribute = [...attributeValues]
        const newAttribute = oldAttribute.filter((obj) => obj.id !== attId)
        setAttributeValues([...newAttribute])
        setFunc('attributes', [...newAttribute])
    }

    return (
        <div className='product-attribute w-75'>
            {attributeValues.map((item) => (
                <div key={item.id} className='d-flex align-items-center justify-content-evenly pt-3'>
                    <select
                        className='form-control me-3'
                        id='selectGroup'
                        value={item.id}
                        defaultValue={item.id}
                        onChange={(e) => onAttributeChange(e, item.id)}
                        disabled={isDisable}
                    >
                        <option selected hidden>
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
                        value={item.value}
                        onChange={(e) => onAttributeValueChange(e, item.id)}
                        disabled={isDisable}
                    />
                    {!isDisable && (
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
                    )}
                </div>
            ))}
            {!isDisable && (
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
            )}
        </div>
    )
}

export default ProductAttribute
