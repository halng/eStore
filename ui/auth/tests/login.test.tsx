import "@testing-library/jest-dom";
import Login from "../src/pages/Login";
import React from "react";
import renderer from "react-test-renderer";
import { BrowserRouter } from "react-router-dom";

describe("Test form rendering", () => {
  it("Render Login component", () => {
    const loginComponent = renderer.create(
      <BrowserRouter>
        <Login />
      </BrowserRouter>
    );

    let tree = loginComponent.toJSON();

    expect(tree).toMatchSnapshot();
  });
});
