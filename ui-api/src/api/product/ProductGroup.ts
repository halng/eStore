import api from '../helper/api'

const ProductGroupAPI = {
    create: (name: string) => api.post(`/product/group?groupName=${name}`),
    getAll: (page: number) => api.get(`/product/group?page=${page}`),
    update: (newName: string, groupId: number) => api.put(`/product/group/${groupId}?newName=${newName}`),
    disableGroup: (groupId: number) => api.patch(`/product/group/${groupId}/disabled`),
    enabledGroup: (groupId: number) => api.patch(`/product/group/${groupId}/enabled`),
    deleteGroup: (groupId: number) => api.delete(`/product/group/${groupId}`),
}

export default ProductGroupAPI
