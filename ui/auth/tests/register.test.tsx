import "@testing-library/jest-dom";
import Register from "../src/pages/Register";
import React from "react";
import renderer from "react-test-renderer";
import { BrowserRouter } from "react-router-dom";

describe("Test form rendering", () => {
  it("Render register component", () => {
    const registerComponent = renderer.create(
      <BrowserRouter>
        <Register />
      </BrowserRouter>
    );

    let treeRegister = registerComponent.toJSON();

    expect(treeRegister).toMatchSnapshot();
  });
});
