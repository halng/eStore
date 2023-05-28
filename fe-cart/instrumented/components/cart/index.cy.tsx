import Cart from ".";

describe("Test Cart Component", () => {
    it("Test text render from Cart component", () => {
        cy.mount(<Cart />)

        cy.get("div").contains("Hello from cart")
    })
})