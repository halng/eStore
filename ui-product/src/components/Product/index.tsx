import React, { useEffect, useState } from 'react'
import Navbar from '../../common/Navbar'
import CreateProduct from './actions/CreateProduct'
import { ProductAPI } from 'api-estore-v2'
import { toast } from 'react-toastify'
import Pagination from '../../common/pagination'
import ProductCard from '../../common/ProductCard'
import { ProductType } from '../../types/ProductType'
import Grid from '@mui/joy/Grid'
import AspectRatio from '@mui/joy/AspectRatio'
import Card from '@mui/joy/Card'
import Skeleton from '@mui/joy/Skeleton'
import Typography from '@mui/joy/Typography'

const Product = () => {
    const [products, setProducts] = useState<ProductType[]>([])
    const [page, setPage] = useState<number>(1)
    const [totalProduct, setTotalProduct] = useState<number>(0)
    const [totalPage, setTotalPage] = useState<number>(0)
    const getAllProduct = () => {
        ProductAPI.getAll(page)
            .then((res) => {
                setProducts(res.data.items)
                setTotalPage(res.data.totalPages)
                setTotalProduct(res.data.totalItems)
            })
            .catch((err) => {
                toast.error('Cannot get all product. Please try again later')
                console.log(err)
            })
    }
    useEffect(() => {
        getAllProduct()
    }, [page])

    return (
        <div className='product-board'>
            <Navbar />
            <div className='product-board'>
                <div className='d-flex justify-content-between pb-3'>
                    <div className='fs-3'>Product</div>
                    <div className='product-group-func d-flex justify-content-end align-items-center'>
                        <div className='search-box d-flex align-items-center me-3'>
                            <svg
                                xmlns='http://www.w3.org/2000/svg'
                                width='18'
                                height='18'
                                fill='currentColor'
                                className='bi bi-search'
                                viewBox='0 0 16 16'
                            >
                                <path d='M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z' />
                            </svg>
                            <input className='me-2 ps-3 py-2' type='text' placeholder='Search' aria-label='Search' />
                        </div>

                        <button
                            type='button'
                            className='btn btn-primary'
                            data-bs-toggle='offcanvas'
                            data-bs-target='#offcanvasRight'
                            aria-controls='offcanvasRight'
                        >
                            Create new product
                        </button>

                        <div
                            className='offcanvas offcanvas-end w-75'
                            tabIndex={-1}
                            id='offcanvasRight'
                            aria-labelledby='offcanvasRightLabel'
                        >
                            <div className='offcanvas-header'>
                                <h5 className='offcanvas-title' id='offcanvasRightLabel'>
                                    Create New Product:
                                </h5>
                                <button
                                    type='button'
                                    className='btn-close'
                                    data-bs-dismiss='offcanvas'
                                    aria-label='Close'
                                ></button>
                            </div>
                            <div className='offcanvas-body d-flex flex-column align-content-center'>
                                <CreateProduct />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className='product-main-board'>
                <Grid container spacing={{ xs: 2 }} sx={{ flexGrow: 1 }}>
                    {products.length > 0
                        ? (products || []).map((item) => <ProductCard key={item.slug} item={item} />)
                        : [...Array(12).keys()].map((index) => (
                              <div key={index} className='p-2'>
                                  <Card variant='outlined' sx={{ width: 343, display: 'flex', gap: 2 }}>
                                      <AspectRatio ratio='21/9'>
                                          <Skeleton variant='overlay'>
                                              <img
                                                  alt=''
                                                  src='data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs='
                                              />
                                          </Skeleton>
                                      </AspectRatio>
                                      <Typography>
                                          <Skeleton>
                                              Lorem ipsum is placeholder text commonly used in the graphic, print, and
                                              publishing industries.
                                          </Skeleton>
                                      </Typography>
                                  </Card>
                              </div>
                          ))}
                </Grid>
            </div>
            {totalPage > 1 && (
                <Pagination total={totalProduct} currentPage={page} totalPage={totalPage} setPage={setPage} />
            )}
        </div>
    )
}

export default Product
