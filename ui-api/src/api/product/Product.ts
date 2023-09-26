import api from '../helper/api'

const ProductAPI = {
    create: (data: any) => api.post('/product', data),
}

export default ProductAPI
