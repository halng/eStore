import React, { useState, useEffect } from 'react'
import { CommonType } from '../../../../types/CommonType'
import { ProductOptionAPI } from 'api-estore-v2'
import { toast } from 'react-toastify'
import { OptionCombineType, OptionValueType, VariationCreateType } from '../../../../types/ProductVariationType'

const ProductVariation = () => {
    const [options, setOptions] = useState<CommonType[]>([])
    const [currentOptionId, setCurrentOptionId] = useState<string>('')
    const [optionValue, setOptionValue] = useState<OptionValueType[]>([])

    useEffect(() => {
        ProductOptionAPI.getOption()
            .then((res) => {
                setOptions(res.data)
            })
            .catch(() => {
                toast.error('Cannot get all option! Please try again later')
            })
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

    const getNumberOfProductVariation = () => {
        let result = 1
        for (const op of optionValue) {
            result = result * op.value.length
        }
        return result
    }

    const getOptionCombine = () => {
        const result: OptionCombineType[][] = new Array(getNumberOfProductVariation())
            .fill(undefined)
            .map(() => new Array(optionValue.length).fill(undefined))

        let row = 0
        for (let i = 0; i < optionValue.length; i++) {
            while (row < 4) {
                for (let j = 0; j < optionValue[i].value.length; j++) {
                    const optionCombine: OptionCombineType = {
                        optionId: optionValue[i].type?.id,
                        optionName: optionValue[i].type?.name,
                        optionValue: optionValue[i].value[j],
                    }
                    result[row][i] = optionCombine
                    row++
                }
            }
            row = 0
        }

        return result
    }

    const isNotFull = (arr: string[], total: number, searchStr: string) => {
        let count = 0
        arr.forEach((a) => {
            if (a === searchStr) {
                count++
            }
        })

        return count < total
    }

    const generateOptionVariation = () => {
        const totalVariationElement = getNumberOfProductVariation()
        const result: OptionCombineType[][] = new Array(totalVariationElement)
            .fill(undefined)
            .map(() => new Array(optionValue.length).fill(undefined))

        const tempResult: string[] = new Array(totalVariationElement).fill(undefined) //TODO: get product name and fill here

        optionValue.forEach((item) => {
            let col = 0
            while (col < totalVariationElement) {
                const amountLoop = totalVariationElement / item.value.length
                item.value.forEach((v) => {
                    const searchStr = tempResult[col] + v
                    if (isNotFull(tempResult, amountLoop, searchStr)) {
                        result[col].push({
                            optionId: item.type?.id,
                            optionName: item.type?.name,
                            optionValue: v,
                        })
                        tempResult[col] = searchStr
                        col++
                    }
                })
            }
        })

        // let isFirstTime = true

        // let row = 0
        // optionValue.forEach((item) => {
        //     let col = 0
        //     let check = 0
        //     while (col < totalVariationElement) {
        //         let i = check
        //         for (; i < item.value.length; ) {
        //             const currentValue = item.value[i]
        //             // if (isFirstTime) {
        //             result[col][row] = {
        //                 optionId: item.type?.id,
        //                 optionName: item.type?.name,
        //                 optionValue: currentValue,
        //             }
        //             tempResult[col] = tempResult[col] + currentValue
        //             col++

        //             if (check === item.value.length - 1) {
        //                 i = 0
        //                 check = 0
        //             } else {
        //                 i++
        //             }
        //             if (col === totalVariationElement) {
        //                 break
        //             }
        //         }
        //         if (!isFirstTime) {
        //             check++
        //         }
        //     }
        //     isFirstTime = false
        //     row++
        // })

        return result
    }

    const onGenerateVariationHandler = () => {
        if (isFill()) {
            console.log(optionValue)
            console.log(generateOptionVariation())
        } else {
            toast.warn('Please help to fill all value for all options')
        }
    }

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
                <div className='text-center'>
                    <button type='button' className='btn btn-info' onClick={onGenerateVariationHandler}>
                        Generate
                    </button>
                </div>
            )}
        </div>
    )
}

export default ProductVariation
