import axios from 'axios'
import {
    getAccessToken,
    getRefreshToken,
    isIgnore,
    saveAccessToken,
    updateAccessToken,
    saveRefreshToken,
} from './token'

const BASE_URL = 'http://localhost:9091/api/v1/'

const api = axios.create({
    baseURL: BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
})

api.interceptors.request.use((config) => {
    if (!isIgnore(config.url)) {
        const token = getAccessToken()
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`
        }
    }
    return config
})

api.interceptors.response.use(
    (res) => {
        if (res.config.url === '/auth/login') {
            const data = res.data
            saveAccessToken(data.accessToken)
            saveRefreshToken(data.refreshToken)
        }
        return res
    },
    async (err) => {
        const originalConfig = err.config

        if (originalConfig.url !== '/auth/login' && err.response) {
            // access token was expired
            if (err.response.status === 401 && !originalConfig._retry) {
                originalConfig._retry = true
                try {
                    const rs = await api.post('/auth/refreshtoken', {
                        refreshToken: getRefreshToken(),
                    })

                    const { accessToken } = rs.data
                    updateAccessToken(accessToken)
                    return api(originalConfig)
                } catch (_err) {
                    Promise.reject(_err)
                }
            }
        }
        return Promise.reject(err)
    },
)

export default api
