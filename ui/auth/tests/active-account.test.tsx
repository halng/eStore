import "@testing-library/jest-dom";
import React from "react";
import renderer from "react-test-renderer";
import { BrowserRouter } from "react-router-dom";
import ActiveAccount from "../src/pages/ActiveAccount";

describe("Test form rendering", () => {
  it("Render Login component", () => {
    const loginComponent = renderer.create(
      <BrowserRouter>
        <ActiveAccount />
      </BrowserRouter>
    );

    let tree = loginComponent.toJSON();

    expect(tree).toMatchSnapshot();
  });
});
