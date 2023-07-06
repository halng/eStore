import api from '../helper/api';

const AuthAPI = {
  login: (data: any) => api.post(`/auth/login`, data),
  register: (data: any) => api.post(`/auth/register`, data),
  activeAccount: (token: String, email: String) =>
    api.post(`/auth/active-account/${token}/${email}`),
};

export default AuthAPI;
