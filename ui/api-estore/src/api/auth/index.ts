import axios from 'axios';

const BASE_HOST = 'http://localhost:9090/api/v1/auth';

const AuthAPI = {
  login: (data: any) => axios.post(`${BASE_HOST}/login`, data),
  register: (data: any) => axios.post(`${BASE_HOST}/register`, data),
  activeAccount: (token: String, email: String) =>
    axios.post(`${BASE_HOST}/active-account/${token}/${email}`),
};

export default AuthAPI;
