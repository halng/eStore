import { ProductAttributeValueType } from './ProductAttributeType'
import { ProductSEOType } from './ProductSEOType'

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
    thumbnailId: string | ProductThumbnail
    imageIds: string[] | ProductImageType
    attributes: ProductAttributeValueType[]
    seo: ProductSEOType
}
