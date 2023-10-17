import { CommonType } from './CommonType'

export type OptionValueType = {
    type: CommonType | undefined
    value: string[]
}

export type OptionCombineType = {
    optionId: string | undefined
    optionName: string | undefined
    optionValue: string | undefined
}

export type VariationCreateType = {
    optionCombine: OptionCombineType[]
    name: string
    price: number
    quantity: number
    images?: File[]
}
