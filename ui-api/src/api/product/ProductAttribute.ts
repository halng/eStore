import api from '../helper/api'

const ProductAttributeAPI = {
    getAttribute: () => api.get('product/attribute/all'),
    create: (data: any) => api.post(`/product/attribute`, data),
    getAll: (page: number) => api.get(`/product/attribute?page=${page}`),
    update: (data: any, attributeId: string) => api.put(`/product/attribute/${attributeId}`, data),
    disableAttribute: (attributeId: string) => api.patch(`/product/attribute?attId=${attributeId}&action=disable`),
    enabledAttribute: (attributeId: string) => api.patch(`/product/attribute?attId=${attributeId}&action=enable`),
    deleteAttribute: (attributeId: string) => api.delete(`/product/attribute/${attributeId}`),
}

export default ProductAttributeAPI
