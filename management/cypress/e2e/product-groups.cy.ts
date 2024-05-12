describe('Product Groups Page', () => {
    const mockProductGroupResponse = {
        items: [
            {
                id: 'XXX-XXX-XXX-XX1',
                name: 'test 1',
                description: 'this is test 1',
                status: 'REMOVED',
                updatedDate: '2024-05-11T11:21:51.724458Z',
            },
            {
                id: 'XXX-XXX-XXX-XX2',
                name: 'test 2',
                description: 'this is test 2',
                status: 'REMOVED',
                updatedDate: '2024-05-11T11:21:39.073022Z',
            },
            {
                id: 'XXX-XXX-XXX-XX3',
                name: 'test 3',
                description: 'this is test 3',
                status: 'ENABLED',
                updatedDate: '2024-05-11T11:21:07.268337Z',
            },
            {
                id: 'XXX-XXX-XXX-XX4',
                name: 'test 4',
                description: 'this is test 4',
                status: 'DISABLED',
                updatedDate: '2024-05-11T11:20:45.306826Z',
            },
            {
                id: 'XXX-XXX-XXX-XX5',
                name: 'test 5',
                description: 'this is test 5',
                status: 'ENABLED',
                updatedDate: '2024-05-11T10:24:59.924065Z',
            },
            {
                id: 'XXX-XXX-XXX-XX6',
                name: 'test 6',
                description: 'this is test 6',
                status: 'ENABLED',
                updatedDate: '2024-02-27T12:25:03.768889Z',
            },
            {
                id: 'XXX-XXX-XXX-XX7',
                name: 'test 7',
                description: 'this is test 7',
                status: 'ENABLED',
                updatedDate: '2024-02-12T04:25:01.456719Z',
            },
        ],
        totalPages: 1,
        totalItems: 7,
    }
    beforeEach(() => {
        // Perform any setup or navigation before each test
        cy.visit('http://localhost:3000')
        cy.intercept('POST', 'http://localhost:9090/api/v1/auth/login', {
            fixture: 'seller.json',
        })

        cy.get('input[name="username"]').type('seller')
        cy.get('input[name="password"]').type('seller')
        cy.get('button[type="submit"]').click()

        cy.get("a[href='/partner/product-groups']").click()
        cy.intercept('GET', 'http://localhost:9090/api/v1/product/group?page=1', mockProductGroupResponse).as(
            'getProductGroups',
        )
    })

    it('should display the product groups page correctly', () => {
        // Assert that the page title is correct
        cy.title().should('eq', 'E-Store Management System')
        cy.wait('@getProductGroups').its('response.statusCode').should('eq', 200)

        cy.url().should('contain', '/partner/product-groups')

        cy.get('tr').should('have.length.at.least', mockProductGroupResponse.items.length)

        cy.get(
            '*[class^="MuiInputBase-root MuiInputBase-colorPrimary MuiTablePagination-input css-16c50h-MuiInputBase-root-MuiTablePagination-select"]',
        ).click()

        cy.get('li').contains('25').click()

        cy.get('tr').should('have.length.at.least', mockProductGroupResponse.items.length)
    })

    it('Create new product group', () => {
        cy.wait('@getProductGroups').its('response.statusCode').should('eq', 200)

        cy.get('button').contains('Add').click()

        // assert that the dialog is opened
        cy.get('*[class^="MuiTypography-root MuiTypography-body1 css-ahj2mt-MuiTypography-root"]').should(
            'have.text',
            'To create new Product Group please enter your product group name and product group description here.',
        )

        cy.intercept('POST', 'http://localhost:9090/api/v1/product/group', {
            message: 'Product group: test 8 created successfully',
        }).as('createProductGroup')

        cy.get('input[name="groupName"]').type('test 8')
        cy.get('textarea[name="groupDescription"]').type('this is test 8')
        cy.get('button[type="submit"]').should('have.text', 'CREATE').click()

        cy.wait('@createProductGroup')
            .its('request.body')
            .should('deep.equal', { name: 'test 8', description: 'this is test 8' })

        cy.get('.Toastify__toast-body').should('have.text', 'Product group: test 8 created successfully')
        mockProductGroupResponse.items.push({
            id: 'XXX-XXX-XXX-XX8',
            name: 'test 8',
            description: 'this is test 8',
            status: 'ENABLED',
            updatedDate: new Date().toISOString(),
        })
        cy.intercept('GET', 'http://localhost:9090/api/v1/product/group?page=1', mockProductGroupResponse).as(
            'getProductGroups',
        )
        cy.reload()
        cy.wait('@getProductGroups').its('response.statusCode').should('eq', 200)
        cy.get('tr').should('have.length.at.least', mockProductGroupResponse.items.length)
    })

    it('Update product group name', () => {
        const updatedName = 'updated test 7'
        cy.wait('@getProductGroups').its('response.statusCode').should('eq', 200)

        // Find the product group with name 'test 8' and update its name
        cy.get('tr').contains('test 7').parent().find('button').click()
        cy.get('li').contains('Edit').click()

        // make sure the name of the product group is bold
        cy.get('span').contains('test 7').should('have.css', 'color', 'rgb(211, 47, 47)')

        cy.get(
            '*[class^="MuiTypography-root MuiDialogContentText-root MuiTypography-body1 MuiDialogContentText-root css-1l3chza-MuiTypography-root-MuiDialogContentText-root"]',
        ).should(
            'have.text',
            'To update exist Product Group: test 7 please enter your new product group name and new product group description here.',
        )

        cy.intercept('PUT', 'http://localhost:9090/api/v1/product/group', {
            message: `Product group: ${updatedName} updated successfully`,
        }).as('updateProductGroup')

        cy.get('input[name="groupName"]').clear().type(updatedName)
        cy.get('button[type="submit"]').should('have.text', 'UPDATE').click()

        cy.wait('@updateProductGroup').its('request.body').should('deep.equal', {
            id: 'XXX-XXX-XXX-XX7',
            name: updatedName,
            description: 'this is test 7',
            status: 'ENABLED',
        })

        cy.get('.Toastify__toast-body').should('have.text', 'Product group: updated test 7 updated successfully')
        const updatedProductGroup = mockProductGroupResponse.items.find((item) => item.name === 'test 7')
        if (updatedProductGroup) {
            updatedProductGroup.name = updatedName
        }

        cy.intercept('GET', 'http://localhost:9090/api/v1/product/group?page=1', mockProductGroupResponse).as(
            'getProductGroups',
        )
        cy.reload()
        cy.wait('@getProductGroups').its('response.statusCode').should('eq', 200)
        cy.get('tr').should('contain', updatedName)
    })
    it('Delete product group', () => {
        cy.wait('@getProductGroups').its('response.statusCode').should('eq', 200)
        // Find the product group with name 'test 8' and delete it
        cy.get('tr').contains('test 8').parent().find('button').click()
        cy.get('li').contains('Delete').click()

        // assert that the delete confirmation dialog is opened
        cy.get('span').contains('test 8').should('have.css', 'color', 'rgb(211, 47, 47)')

        cy.get(
            '*[class^="MuiTypography-root MuiDialogContentText-root MuiTypography-body1 MuiDialogContentText-root css-1l3chza-MuiTypography-root-MuiDialogContentText-root"]',
        ).should('have.text', 'Are you sure you want to delete this product group test 8?')

        cy.intercept('DELETE', 'http://localhost:9090/api/v1/product/group/XXX-XXX-XXX-XX8', {
            message: 'Product group: test 8 deleted successfully',
        }).as('deleteProductGroup')

        cy.get('button[type="submit"]').should('have.text', 'DELETE').click()

        cy.wait('@deleteProductGroup')
            .its('request.url')
            .should('eq', 'http://localhost:9090/api/v1/product/group/XXX-XXX-XXX-XX8')
        cy.get('.Toastify__toast-body').should('have.text', 'Product group: test 8 deleted successfully')

        const deletedProductGroupIndex = mockProductGroupResponse.items.findIndex((item) => item.name === 'test 8')
        mockProductGroupResponse.items.splice(deletedProductGroupIndex, 1)
        cy.intercept('GET', 'http://localhost:9090/api/v1/product/group?page=1', mockProductGroupResponse).as(
            'getProductGroups',
        )
        cy.reload()
        cy.wait('@getProductGroups').its('response.statusCode').should('eq', 200)
        cy.get('tr').should('not.contain', 'test 8')
    })
})
