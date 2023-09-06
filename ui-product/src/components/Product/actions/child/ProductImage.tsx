import React, { useState } from 'react'
import { ProductImageType, ProductThumbnail } from '../../../../types/ProductType'

const ProductImage = () => {
    const [thumbnail, setThumbnail] = useState<ProductThumbnail>({ url: '' })
    const [images, setImages] = useState<ProductImageType>({ urls: [], files: [] })

    const onUploadThumbnail = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            const src = URL.createObjectURL(e.target.files[0])
            setThumbnail({ file: e.target.files[0], url: src })
        }
    }

    const onUploadImage = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            const _urls: string[] = []
            const _files: File[] = []

            const fileList = [...e.target.files]
            fileList.map((item) => {
                const url = URL.createObjectURL(item)
                _urls.push(url)
                _files.push(item)
            })

            setImages({ urls: _urls, files: _files })
        }
    }

    return (
        <div className='product-attribute w-100'>
            <div className='thumbnail '>
                <div className='input-group mb-3'>
                    <label className='input-group-text' htmlFor='inputGroupFile01'>
                        Upload Thumbnail
                    </label>
                    <input
                        type='file'
                        className='form-control'
                        id='inputGroupFile01'
                        accept='image/*'
                        onChange={(e) => onUploadThumbnail(e)}
                    />
                </div>
                <div>{thumbnail.url && <img src={thumbnail.url} width={250} height={250} />}</div>
            </div>
            <div className='image'>
                <div className='input-group mb-3'>
                    <label className='input-group-text' htmlFor='inputGroupFile01'>
                        Upload Product Image
                    </label>
                    <input
                        type='file'
                        multiple
                        className='form-control'
                        id='inputGroupFile01'
                        accept='image/*'
                        onChange={(e) => onUploadImage(e)}
                    />
                </div>
                <div>
                    {(images.urls || []).map((item, index) => (
                        <img src={item} width={250} height={250} key={index} />
                    ))}
                </div>
            </div>
        </div>
    )
}

export default ProductImage
