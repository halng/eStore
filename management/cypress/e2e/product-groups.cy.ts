describe('Product Groups Page', () => {
    beforeEach(() => {
        // Perform any setup or navigation before each test
        cy.visit('http://localhost:3000')
        cy.intercept('POST', 'http://localhost:9090/api/v1/auth/login', {
            fixture: 'seller.json',
        })

        cy.get('input[name="username"]').type('seller')
        cy.get('input[name="password"]').type('seller')
        cy.get('button[type="submit"]').click()
    })

    it('should display the product groups page', () => {
        // Assert that the page title is correct
        cy.title().should('eq', 'E-Store Management System')

        // Assert that the page contains a specific element
        cy.get('h6').should('contain', 'Tao Nguyen')
        cy.get('p').should('contain', 'SELLER')

        cy.get("a[href='/partner/product-groups']").click()

        cy.url().should('contain', '/partner/product-groups')
    })

    it('Should update table view when change row per page', () => {
        cy.get("a[href='/partner/product-groups']").click()

        cy.get('tr').should('have.length', 11)

        cy.get(
            '*[class^="MuiInputBase-root MuiInputBase-colorPrimary MuiTablePagination-input css-16c50h-MuiInputBase-root-MuiTablePagination-select"]',
        ).click()

        cy.get('li').contains('25').click()

        cy.get('tr').should('have.length', 26)
    })
})
