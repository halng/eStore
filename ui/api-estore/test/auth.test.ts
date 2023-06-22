// import  axios  from 'axios';
import authAPI from "../src/auth"

// jest.mock("axios")

describe('Test Auth API', () => {
it("Return Unauthenticated when password or username wrong", async () => {
    expect(authAPI.login.mock).toBeTruthy();

    const returnData = {
            "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTY4NzQyNTg4MCwiZXhwIjoxNjg3NDYxODgwfQ.FYq4V6KpTbcOTKhqd6vAfVuVX1OJGjl3y8qwA82S7NkdzJgaoGXvillSNy9INOAd7ZXZ5T0qerUBNSVR5PFEAA",
            "refreshToken": "4ed1a9d1-8dd4-4330-bf25-2329afec1fdc",
            "accountId": "a9eeaeb7-7eaf-48e9-8bd7-f1e80245406b",
            "email": "admin@estore.com",
            "username": "admin",
            "role": "ADMIN"
        };
    // axios.get.mockResolvedValue(returnData)

    const body = {username: "admin", password:"admin"};
    const result = authAPI.login.mockReturnValue(returnData)

    expect(axios.get).toHaveBeenCalledWith("")
    expect(result.data).toEqual(returnData)
})

});