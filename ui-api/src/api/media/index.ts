import api from '../helper/api'

const MediaAPI = {
    uploadImages: (formData: any) =>
        api.post('/media/images', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        }),
    get: (id: string) => api.get(`/media/${id}`),
}

export default MediaAPI
