describe('Test E2E for Cart Section', () => {

  it("Test", () => {
    cy.visit("http://localhost:3000/")

    cy.get("div").contains("Hello from cart")
})
})