import api from '../helper/api'

const ProductAPI = {
    create: (data: any) => api.post('/product', data),
    getAll: (page: number) => api.get(`/product?page=${page}`),
    getById: (id: string) => api.get(`/product/${id}`),
    updateProduct: (id: string, data: any) => api.put(`/product/${id}`, data),
    changeStatus: (id: string, action: string) => api.patch(`/product/${id}/${action}`),
}

export default ProductAPI
