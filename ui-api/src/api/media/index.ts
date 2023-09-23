import api from '../helper/api'

const MediaAPI = {
    uploadImages: (formData: any) =>
        api.post('/media/images', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        }),
}

export default MediaAPI
