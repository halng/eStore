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

interface Props {
    productSlug: string
}

const ProductAction = {
    CREATE: 'create',
    UPDATE: 'update',
    VIEW: 'view',
    DELETE: 'delete',
}

const CreateProduct = ({ productSlug }: Props) => {
    const [currentTab, setCurrentTab] = useState<number>(0)
    const { handleSubmit, setValue, getValues } = useForm<ProductCreateType>()
    const [openBackdrop, setOpenBackdrop] = useState<boolean>(false)
    const [action, setAction] = useState<string>('')

    useEffect(() => {
        if (productSlug) {
            setAction(ProductAction.VIEW)
        } else {
            setAction(ProductAction.CREATE)
        }
    }, [productSlug])

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
        <ProductVariation key={'variation'} setFunc={setValue} getFunc={getValues} />,
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
                {action === ProductAction.VIEW && (
                    <>
                        <div>
                            <button type='button' className='me-3 btn btn-warning'>
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
                                Save
                            </button>
                        </div>
                        <div>
                            <button type='button' className='me-3 btn btn-warning'>
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
                    Creating...
                </Link>
            </Backdrop>
        </form>
    )
}

export default CreateProduct
