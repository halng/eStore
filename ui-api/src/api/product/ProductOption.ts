import api from '../helper/api'

const ProductOptionAPI = {
    create: (data: any) => api.post(`/product/option`, data),
    getAll: (page: number) => api.get(`/product/option?page=${page}`),
    update: (data: any, optionId: string) => api.put(`/product/option/${optionId}`, data),
    delete: (optionId: string) => api.delete(`/product/option/${optionId}`),
}

export default ProductOptionAPI
