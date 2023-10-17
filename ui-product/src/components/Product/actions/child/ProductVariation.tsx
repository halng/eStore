import React, { useState, useEffect } from 'react'
import { CommonType } from '../../../../types/CommonType'
import { ProductOptionAPI } from 'api-estore-v2'
import { toast } from 'react-toastify'
import { OptionCombineType, OptionValueType, VariationCreateType } from '../../../../types/ProductVariationType'
import { UseFormSetValue, UseFormGetValues } from 'react-hook-form'
import { ProductCreateType } from '../../../../types/ProductType'
import * as _ from 'lodash'
interface props {
    setFunc: UseFormSetValue<ProductCreateType>
    getFunc: UseFormGetValues<ProductCreateType>
}

const ProductVariation = ({ setFunc, getFunc }: props) => {
    const [options, setOptions] = useState<CommonType[]>([])
    const [currentOptionId, setCurrentOptionId] = useState<string>('')
    const [optionValue, setOptionValue] = useState<OptionValueType[]>([])
    const [productVariation, setProductVariation] = useState<VariationCreateType[]>([])

    useEffect(() => {
        ProductOptionAPI.getOption()
            .then((res) => {
                setOptions(res.data)
            })
            .catch(() => {
                toast.error('Cannot get all option! Please try again later')
            })
        const old = getFunc('variations')
        if (old) {
            setProductVariation([...old])
        }
    }, [])

    const onAddOptionHandler = () => {
        if (currentOptionId !== '') {
            const isExist = optionValue.find((o) => o.type?.id === currentOptionId)
            if (isExist) {
                toast.error('This option has been selected. Please try other option(s).')
            } else {
                const currentOption: CommonType | undefined = options.find((obj) => obj.id === currentOptionId)
                const newVariation: OptionValueType = {
                    type: currentOption,
                    value: [],
                }

                setOptionValue([...optionValue, newVariation])
                setCurrentOptionId('')
            }
        } else {
            toast.error('Please choose option first')
        }
    }

    const onOptionValueChangeHandler = (e: React.ChangeEvent<HTMLInputElement>, optionId: string | undefined) => {
        if (optionId) {
            const oldVariation = [...optionValue]
            const index = optionValue.findIndex((obj) => obj.type?.id === optionId)

            oldVariation[index].value = e.target.value.split(',').map((item) => item.trim())
            setOptionValue([...oldVariation])
        }
    }

    const onRemoveOptionHandler = (optionId: string | undefined) => {
        const filterOption = optionValue.filter((o) => o.type?.id !== optionId)
        setOptionValue([...filterOption])
    }

    // check is all option have value ?
    const isFill = () => {
        const isExist = optionValue.filter((item) => item.value.length === 0)
        return isExist.length === 0
    }

    const generateOptionVariation = () => {
        const productName = getFunc('name')

        let objectNameValue = {}
        optionValue.forEach((opV) => {
            if (opV.type?.name) {
                objectNameValue[opV.type?.name + '_' + opV.type.id] = opV.value
            }
        })

        let productVariation = []
        for (const [option, value] of Object.entries(objectNameValue)) {
            productVariation.push(value.map((v) => ({ [option]: v })))
        }

        productVariation = productVariation.reduce((a, b) => a.flatMap((d) => b.map((e) => ({ ...d, ...e }))))

        const result: VariationCreateType[] = []
        productVariation.forEach((element: any) => {
            const optionCombine: OptionCombineType[] = []
            let variationName = ''

            for (const [k, v] of Object.entries(element)) {
                optionCombine.push({
                    optionId: k.split('_')[1],
                    optionName: k.split('_')[0],
                    optionValue: v,
                })
                variationName = variationName + ' - ' + v
            }

            result.push({
                name: productName + variationName,
                optionCombine: optionCombine,
                price: 0,
                quantity: 0,
            })
        })

        return result
    }

    const onGenerateVariationHandler = () => {
        if (isFill()) {
            const result: VariationCreateType[] = generateOptionVariation()
            setFunc('variations', result)
            setProductVariation([...result])
        } else {
            toast.warn('Please help to fill all value for all options')
        }
    }

    const onUpdatePriceVariationHandler = (name: string, e: React.ChangeEvent<HTMLInputElement>) => {
        const oldList = [...productVariation]
        const index = _.findIndex(oldList, { name: name })
        const oldObj = oldList[index]
        oldList.splice(index, 1, {
            name: name,
            optionCombine: oldObj.optionCombine,
            price: parseInt(e.target.value),
            quantity: oldObj.quantity,
            // images: oldObj.images,
        })

        setProductVariation([...oldList])
        setFunc('variations', [...oldList])
    }

    const onUpdateQuantityVariationHandler = (name: string, e: React.ChangeEvent<HTMLInputElement>) => {
        const oldList = [...productVariation]
        const index = _.findIndex(oldList, { name: name })
        const oldObj = oldList[index]
        oldList.splice(index, 1, {
            name: name,
            optionCombine: oldObj.optionCombine,
            price: oldObj.price,
            quantity: parseInt(e.target.value),
            // images: oldObj.images,
        })

        setProductVariation([...oldList])
        setFunc('variations', [...oldList])
    }

    const onRemoveVariationHandler = (name: string) => {
        const newList = _.remove(productVariation, (p) => p.name !== name)
        setProductVariation([...newList])
        setFunc('variations', [...newList])
    }

    // const onUploadImageVariationHandler = (name: string, e: React.ChangeEvent<HTMLInputElement>) => {
    //     if (e.target.files) {
    //         const oldList = [...productVariation]
    //         const index = _.findIndex(oldList, { name: name })
    //         const oldObj = oldList[index]
    //         const listImage = [...e.target.files]
    //         const newList = oldList.splice(index, 1, {
    //             name: name,
    //             optionCombine: oldObj.optionCombine,
    //             price: oldObj.price,
    //             quantity: oldObj.quantity,
    //             // images: listImage,
    //         })
    //         setProductVariation([...newList])
    //         setFunc('variations', [...newList])
    //     }
    // }

    return (
        <div className='product-variation w-75'>
            <div className='row mb-3'>
                <label className='col-sm-2 col-form-label' htmlFor='selectOption'>
                    Options
                </label>
                <div className='col-sm-8'>
                    <select
                        className='form-control me-3'
                        id='selectOption'
                        value={currentOptionId}
                        onChange={(e) => setCurrentOptionId(e.target.value)}
                    >
                        <option value='' hidden>
                            Choose Option
                        </option>
                        {(options || []).map((att) => (
                            <option value={att.id} key={att.id}>
                                {att.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className='col-sm-2'>
                    <button type='button' className='btn btn-info' onClick={onAddOptionHandler}>
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

            {/* generate input field to add value for product variation*/}
            {(optionValue || []).map((item) => (
                <div className='row mb-3' key={item.type?.id}>
                    <label htmlFor={`input_${item.type?.name}`} className='col-sm-3 col-form-label'>
                        Option: <span className='text-info'>{item.type?.name}</span>
                    </label>
                    <div className='col-sm-7'>
                        <input
                            type='text'
                            className='form-control'
                            value={item.value.toString()}
                            onChange={(e) => onOptionValueChangeHandler(e, item.type?.id)}
                            id={`input_${item.type?.name}`}
                        />
                    </div>
                    <div className='col-sm-2'>
                        <button
                            type='button'
                            className='btn btn-outline-danger '
                            onClick={() => onRemoveOptionHandler(item.type?.id)}
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
                </div>
            ))}
            {/* generate product variation button */}
            {optionValue.length > 0 && (
                <>
                    <div className='text-center'>
                        <button type='button' className='btn btn-info' onClick={onGenerateVariationHandler}>
                            Generate
                        </button>
                    </div>
                    <div className='text-left text-primary mt-3 h3'>Variations:</div>
                </>
            )}
            {/* generate product variation for input value */}
            <div className='variation-item my-3'>
                {(productVariation || []).map((item) => (
                    <div className='align-items-center row mt-1' key={item.name}>
                        <div className='col-sm-2 text-info'>{item.name}</div>
                        <div className='col-sm-2'>
                            <input
                                type='text'
                                placeholder='Price'
                                className='form-control'
                                onChange={(e) => onUpdatePriceVariationHandler(item.name, e)}
                            />
                        </div>
                        <div className='col-sm-2'>
                            <input
                                type='text'
                                placeholder='Quantity'
                                className='form-control'
                                onChange={(e) => onUpdateQuantityVariationHandler(item.name, e)}
                            />
                        </div>
                        {/* <div className='col-sm-4'>
                            <input
                                type='file'
                                multiple
                                className='form-control'
                                id='inputProductImage'
                                accept='image/*'
                                onChange={(e) => onUploadImageVariationHandler(item.name, e)}
                            />
                        </div> */}
                        <div className='col-sm-2'>
                            <button
                                type='button'
                                className='btn btn-outline-danger '
                                onClick={() => onRemoveVariationHandler(item.name)}
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
                    </div>
                ))}
            </div>
        </div>
    )
}

export default ProductVariation
