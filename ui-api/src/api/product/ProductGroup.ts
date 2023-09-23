import api from '../helper/api'

const ProductGroupAPI = {
    getGroup: () => api.get('product/group/all'),
    create: (name: string) => api.post(`/product/group?groupName=${name}`),
    getAll: (page: number) => api.get(`/product/group?page=${page}`),
    update: (newName: string, groupId: string) => api.put(`/product/group/${groupId}?newName=${newName}`),
    disableGroup: (groupId: string) => api.patch(`/product/group/${groupId}/disabled`),
    enabledGroup: (groupId: string) => api.patch(`/product/group/${groupId}/enabled`),
    deleteGroup: (groupId: string) => api.delete(`/product/group/${groupId}`),
}

export default ProductGroupAPI
