import { ProductAttributeValueType } from './ProductAttributeType'
import { ProductSEOType } from './ProductSEOType'
import { VariationCreateType } from './ProductVariationType'

export type ProductThumbnail = {
    url: string
    file?: File
}

export type ProductImageType = {
    urls?: string[]
    files?: File[]
}

export type ProductCreateType = {
    name: string
    slug: string
    price: number
    quantity: number
    group: string
    description: string
    thumbnail: ProductThumbnail
    images: ProductImageType
    attributes: ProductAttributeValueType[]
    seo: ProductSEOType
    variations: VariationCreateType[]
}

export type ProductType = {
    slug: string
    thumbnail: string
    name: string
    price: number
    quantity: string
    variations: number
    groupName: string
}
