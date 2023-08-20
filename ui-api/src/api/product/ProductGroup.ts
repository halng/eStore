import api from '../helper/api'

const ProductGroupAPI = {
    create: (name: string) => api.post(`/product/group?groupName=${name}`),
}

export default ProductGroupAPI
