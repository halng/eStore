import axios from 'axios';

const BASE_HOST = 'http://localhost:9090/api/v1/auth';

const AuthAPI = {
  login: (data: any) => axios.post(`${BASE_HOST}/login`, data),
  register: (data: any) => axios.post(`${BASE_HOST}/register`, data),
  verifyAccount: (accountId: String, token: String) =>
    axios.post(`${BASE_HOST}/verify/${accountId}/${token}`),
};

export default AuthAPI;