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
