import React, { useState } from 'react'
import { ProductAttribute, ProductBasicInfo, ProductImage, ProductPost, ProductSEO, ProductVariation } from './child'

const CreateProduct = () => {
    const tabs = ['Basic Info', 'Images', 'Blog Post', 'Product Variation', 'Product Attribute', 'SEO']
    const tabComponent = [
        <ProductBasicInfo key={'basic-info'} />,
        <ProductImage key={'image'} />,
        <ProductPost key={'post'} />,
        <ProductVariation key={'variation'} />,
        <ProductAttribute key={'attribute'} />,
        <ProductSEO key={'seo'} />,
    ]

    const [currentTab, setCurrentTab] = useState<number>(0)

    return (
        <div>
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
                    <button type='button' className='me-3 btn btn-primary'>
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
        </div>
    )
}

export default CreateProduct
