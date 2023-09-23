import React, { useState } from 'react'
import { ProductAttribute, ProductBasicInfo, ProductImage, ProductPost, ProductSEO } from './child'
import { useForm, SubmitHandler } from 'react-hook-form'
import { ProductCreateType } from '../../../types/ProductType'
import { toast } from 'react-toastify'
import { MediaAPI } from 'api-estore-v2'

const CreateProduct = () => {
    const [currentTab, setCurrentTab] = useState<number>(0)
    const { handleSubmit, setValue, getValues } = useForm<ProductCreateType>()

    const uploadFile = (data: any): string[] | any => {
        MediaAPI.uploadImages(data)
            .then((res) => {
                return res.data['itemIds']
            })
            .catch(() => {
                toast.error('Cannot Upload Image(s)')
            })
    }
    const onSubmitHandler: SubmitHandler<ProductCreateType> = (data) => {
        const form: any = new FormData()
        form.append('caption', getValues('name'))

        // upload thumbnail first
        const thumbnail = getValues('thumbnailId.file')
        form.append('files', thumbnail)
        const thumbnailIds = uploadFile(form)
        form.delete('files')

        //upload images
        const images = getValues('imageIds.files')
        images?.forEach((img) => {
            form.append('files', img)
        })
        const imageIds = uploadFile(form)

        // create product
        setValue('imageIds', imageIds)
        setValue('thumbnailId', thumbnailIds)
        console.log(data)
    }

    const onInputValueChange = (
        fieldName: any,
        e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLSelectElement>,
    ) => {
        setValue(fieldName, e.target.value)
    }

    const getInputValue = (fieldName: any) => getValues(fieldName)

    const tabs = ['Basic Info', 'Images', 'Blog Post', 'Product Attribute', 'SEO']
    const tabComponent = [
        <ProductBasicInfo key={'basic-info'} setFunc={onInputValueChange} getFunc={getInputValue} />,
        <ProductImage key={'image'} setFunc={setValue} getFunc={getValues} />,
        <ProductPost key={'post'} />,
        // <ProductVariation key={'variation'} />,
        <ProductAttribute key={'attribute'} setFunc={setValue} getFunc={getValues} />,
        <ProductSEO key={'seo'} setFunc={onInputValueChange} getFunc={getInputValue} />,
    ]
    return (
        <form onSubmit={handleSubmit(onSubmitHandler)}>
            <div className='create-product-header'>
                <ul className='nav nav-tabs'>
                    {tabs.map((item, index) => (
                        <li className='nav-item' key={item.toLowerCase().replace(' ', '')}>
                            <a
                                className={currentTab === index ? 'nav-link active' : 'nav-link'}
                                role='button'
                                aria-current='page'
                                onClick={() => setCurrentTab(index)}
                            >
                                {item}
                            </a>
                        </li>
                    ))}
                </ul>
            </div>
            <div className='create-product-body pt-3  d-flex justify-content-center'>{tabComponent[currentTab]}</div>

            {/* create-product-footer */}
            <div className='d-flex justify-content-center w-100 pt-3'>
                <div>
                    <button
                        type='button'
                        className='me-3 btn btn-light'
                        disabled={currentTab === 0}
                        onClick={() => setCurrentTab(currentTab - 1)}
                    >
                        Previous
                    </button>
                </div>
                <div>
                    <button type='button' className='me-3 btn btn-warning'>
                        Cancel
                    </button>
                </div>
                <div>
                    <button type='submit' className='me-3 btn btn-primary'>
                        Save
                    </button>
                </div>
                <div>
                    <button
                        type='button'
                        className='me-3 btn btn-light'
                        disabled={currentTab === tabs.length - 1}
                        onClick={() => setCurrentTab(currentTab + 1)}
                    >
                        Next
                    </button>
                </div>
            </div>
        </form>
    )
}

export default CreateProduct
