describe('template spec', () => {
    it('passes', () => {
      cy.visit('http://localhost:3000')
      cy.get("div").contains("Hello from cart")
    })
  })