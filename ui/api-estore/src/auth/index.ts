import axios from "axios";

const BASE_HOST = "http://localhost:9090/api/v1/auth"

const authAPI = {
    login: (data: any) => axios.post(`${BASE_HOST}/login`, data),
    register: (data: any) => axios.post(`${BASE_HOST}/register`, data)
}

export default authAPI