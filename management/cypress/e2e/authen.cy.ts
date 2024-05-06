import { setAuth } from '../../src/stores/authSlice'

describe('App Page Login Error', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000')
    })
    it('should alert error when username is empty', () => {
        cy.get('input[name="password"]').type('seller')
        cy.get('button[type="submit"]').click()
        cy.get('p.text-danger').should('have.text', 'Username is required')
    })
    it('should alert error when invalid password or username', () => {
        cy.intercept(
            'POST',
            'http://localhost:9090/api/v1/auth/login',

            { statusCode: 401 },
        )

        cy.get('input[name="username"]').type('seller')
        cy.get('input[name="password"]').type('seller')
        cy.get('button[type="submit"]').click()

        cy.get('p.text-danger').should('have.text', 'Invalid username or password')
    })
})

describe('App Page Login Success And Navigate', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000')
    })
    it('should navigate to partner page', () => {
        cy.intercept('POST', 'http://localhost:9090/api/v1/auth/login', {
            fixture: 'seller.json',
        })

        cy.get('input[name="username"]').type('seller')
        cy.get('input[name="password"]').type('seller')
        cy.get('button[type="submit"]').click()

        cy.url().should('eq', 'http://localhost:3000/partner')
    })

    it('should navigate to manage page', () => {
        cy.intercept('POST', 'http://localhost:9090/api/v1/auth/login', {
            fixture: 'admin.json',
        })

        cy.get('input[name="username"]').type('seller')
        cy.get('input[name="password"]').type('seller')
        cy.get('button[type="submit"]').click()

        cy.url().should('eq', 'http://localhost:3000/management')
    })
})

describe('Check authentication func', () => {
    it('should display the user name and role', () => {
        cy.visit('http://localhost:3000')
        cy.intercept('POST', 'http://localhost:9090/api/v1/auth/login', {
            fixture: 'seller.json',
        })

        cy.get('input[name="username"]').type('seller')
        cy.get('input[name="password"]').type('seller')
        cy.get('button[type="submit"]').click()

        cy.window()
            .its('Cypress')
            .its('store')
            .invoke(
                'dispatch',
                setAuth({
                    isAuth: true,
                    username: 'Seller',
                    photoUrl: 'http://xxx.com',
                    role: 'SELLER',
                    email: 'xxx@email',
                    id: 'xxx',
                }),
            )
        cy.window().its('Cypress').its('store').invoke('getState').its('auth').its('isAuth').should('eq', true)
        cy.window().its('Cypress').its('store').invoke('getState').its('auth').its('email').should('eq', 'xxx@email')

        cy.get('h6').should('have.text', 'Seller')
        cy.get('p').should('contain', 'SELLER')
    })
    it('should redirect to login page when user is not logged in', () => {
        cy.visit('http://localhost:3000/partner')
        cy.url().should('eq', 'http://localhost:3000/')
    })
    it('should redirect to partner page when user is logged in with role seller', () => {
        cy.visit('http://localhost:3000')
        cy.window()
            .its('Cypress')
            .its('store')
            .invoke(
                'dispatch',
                setAuth({
                    isAuth: true,
                    username: 'Seller',
                    photoUrl: 'http://xxx.com',
                    role: 'SELLER',
                    email: 'xxx@email',
                    id: 'xxx',
                }),
            )

        cy.url().should('eq', 'http://localhost:3000/partner')
    })
    it('should redirect to manager page when user is logged in with role seller', () => {
        cy.visit('http://localhost:3000')
        cy.window()
            .its('Cypress')
            .its('store')
            .invoke(
                'dispatch',
                setAuth({
                    isAuth: true,
                    username: 'Admin',
                    photoUrl: 'https://xxx.com',
                    role: 'ADMIN',
                    email: 'admin@email',
                    id: 'xxx',
                }),
            )

        cy.url().should('eq', 'http://localhost:3000/management')
    })
})
