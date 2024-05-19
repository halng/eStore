import api from '../helper/api'

const ProductGroupAPI = {
    getGroup: () => api.get('product/group/all'),
    create: (data: any) => api.post(`/product/group`, data),
    getAll: (page: number) => api.get(`/product/group?page=${page}`),
    update: (data: any) => api.put(`/product/group`, data),
    deleteGroup: (groupId: string) => api.delete(`/product/group/${groupId}`),
}

export default ProductGroupAPI
