import React, { useEffect, useState } from 'react'
import { ProductAttribute, ProductBasicInfo, ProductImage, ProductPost, ProductSEO, ProductVariation } from './child'
import { useForm, SubmitHandler } from 'react-hook-form'
import { ProductCreateType } from '../../../types/ProductType'
import { MediaAPI, ProductAPI } from 'api-estore-v2'
import * as _ from 'lodash'
import { convertStandardSlug } from '../../../utils'
import { toast } from 'react-toastify'
import Backdrop from '@mui/material/Backdrop'
import CircularProgress from '@mui/material/CircularProgress'
import { Link } from '@mui/joy'
import { OptionCombineType, VariationCreateType } from '../../../types/ProductVariationType'

interface Props {
    productSlug: string
}

const ProductAction = {
    CREATE: 'Create',
    UPDATE: 'Update',
    VIEW: 'View',
    DELETE: 'Delete',
}

const CreateProduct = ({ productSlug }: Props) => {
    const [currentTab, setCurrentTab] = useState<number>(0)
    const { handleSubmit, setValue, getValues, reset } = useForm<ProductCreateType>()
    const [openBackdrop, setOpenBackdrop] = useState<boolean>(false)
    const [action, setAction] = useState<string>('')

    useEffect(() => {
        if (productSlug) {
            setAction(ProductAction.VIEW)
            ProductAPI.getProductBySlug(productSlug)
                .then((res) => res.data)
                .then((data) => {
                    const variation: VariationCreateType[] = []
                    const productName = _.get(data, 'name', '')

                    const productOption: any[] = _.get(data, 'productOptionValues', [])
                    const productVariation: any[] = _.get(data, 'productVariations', [])
                    if (productVariation.length !== 0) {
                        productVariation.forEach((ele) => {
                            let optionName = productName
                            const optionCombine: OptionCombineType[] = []
                            const optionId: string[] = _.get(ele, 'optionValueIds', [])
                            optionId.forEach((id) => {
                                const option = productOption.find((_o) => _o.id === id)
                                optionCombine.push({
                                    optionId: _.get(option, 'id', ''),
                                    optionName: _.get(option, 'name', ''),
                                    optionValue: _.get(option, 'value', ''),
                                })
                                optionName = optionName + ` - ${_.get(option, 'value', '')}`
                            })

                            variation.push({
                                optionCombine: optionCombine,
                                name: optionName,
                                price: _.get(ele, 'price', 0),
                                quantity: _.get(ele, 'quantity', 0),
                            })
                        })
                    }

                    const productValue: ProductCreateType = {
                        name: productName,
                        slug: productSlug,
                        price: _.get(data, 'price', ''),
                        quantity: _.get(data, 'quantity', 0),
                        group: _.get(data, ['group', 'id'], ''),
                        description: _.get(data, 'shortDescription', ''),
                        thumbnail: { url: _.get(data, 'thumbnailUrl', '') },
                        images: { urls: _.get(data, 'imageUrls', []) },
                        attributes: _.get(data, 'productAttributeValues', []),
                        seo: {
                            keyword: _.get(data, ['seo', 'keyword'], ''),
                            metadata: _.get(data, ['seo', 'metadata'], ''),
                        },
                        variations: variation,
                    }

                    reset(productValue)
                })
                .catch((err) => console.log(err))
        } else {
            setAction(ProductAction.CREATE)
        }
    }, [productSlug, reset])

    const uploadFile = async (data: any) => {
        const res = await MediaAPI.uploadImages(data)
        return await res.data['items']
    }

    const onSubmitHandler: SubmitHandler<ProductCreateType> = async (data) => {
        setOpenBackdrop(true)
        const form: any = new FormData()
        form.append('caption', `thumbnail${getValues('name')}`)
        const thumbnail = getValues('thumbnail.file')
        form.append('files', thumbnail)
        //upload images
        const form2: any = new FormData()
        form2.append('caption', `image_${getValues('name')}`)
        const images = getValues('images.files')
        images?.forEach((img) => {
            form2.append('files', img)
        })

        Promise.all([uploadFile(form), uploadFile(form2)]).then((values) => {
            const slug = data.slug ? convertStandardSlug(data.slug) : convertStandardSlug(data.name)
            const excludedData = _.omit(data, ['thumbnail', 'images', 'slug']) // exclude file field
            const bodyData = {
                ...excludedData,
                slug: slug,
                thumbnailId: values[0][0],
                imagesIds: values[1],
            }
            ProductAPI.create(bodyData)
                .then((res) => {
                    toast.success(res.data.message)
                    window.location.reload()
                })
                .catch((err) => {
                    console.log(err)
                    toast.error('Create product failed. Please try again later!')
                })
            setOpenBackdrop(false)
        })
    }

    const onInputValueChange = (
        fieldName: any,
        e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLSelectElement>,
    ) => {
        setValue(fieldName, e.target.value)
    }

    const getInputValue = (fieldName: any) => getValues(fieldName)

    const cancelButtonHandler = () => {
        if (action === ProductAction.CREATE) {
            // clear field and close off canva
        } else if (action === ProductAction.UPDATE) {
            // reset value
            setAction(ProductAction.VIEW)
        }
    }

    const tabs = ['Basic Info', 'Images', 'Blog Post', 'Product Variation', 'Product Attribute', 'SEO']
    const tabComponent = [
        <ProductBasicInfo
            key={'basic-info'}
            setFunc={onInputValueChange}
            getFunc={getInputValue}
            isDisable={action === ProductAction.VIEW}
        />,
        <ProductImage key={'image'} setFunc={setValue} getFunc={getValues} />,
        <ProductPost key={'post'} />,
        <ProductVariation
            key={'variation'}
            setFunc={setValue}
            getFunc={getValues}
            isDisable={action === ProductAction.VIEW}
        />,
        <ProductAttribute
            key={'attribute'}
            setFunc={setValue}
            getFunc={getValues}
            isDisable={action === ProductAction.VIEW}
        />,
        <ProductSEO
            key={'seo'}
            setFunc={onInputValueChange}
            getFunc={getInputValue}
            isDisable={action === ProductAction.VIEW}
        />,
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
                {action === ProductAction.VIEW && (
                    <>
                        <div>
                            <button
                                type='button'
                                className='me-3 btn btn-warning'
                                onClick={() => setAction(ProductAction.UPDATE)}
                            >
                                Edit
                            </button>
                        </div>
                        <div>
                            <button type='button' className='me-3 btn btn-danger'>
                                Delete
                            </button>
                        </div>
                    </>
                )}
                {[ProductAction.CREATE, ProductAction.UPDATE].includes(action) && (
                    <>
                        <div>
                            <button type='submit' className='me-3 btn btn-primary'>
                                {action}
                            </button>
                        </div>
                        <div>
                            <button type='button' className='me-3 btn btn-warning' onClick={cancelButtonHandler}>
                                Cancel
                            </button>
                        </div>
                    </>
                )}

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
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={openBackdrop}>
                <Link component='button' variant='plain' startDecorator={<CircularProgress />} sx={{ p: 1 }}>
                    {action === ProductAction.CREATE ? 'Creating ...' : 'Updating ...'}
                </Link>
            </Backdrop>
        </form>
    )
}

export default CreateProduct
