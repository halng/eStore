import "@testing-library/jest-dom";
import ForgotPassword from "../src/pages/ForgotPassword";
import React from "react";
import renderer from "react-test-renderer";
import { BrowserRouter } from "react-router-dom";

describe("Test form rendering", () => {
  it("Render Login component", () => {
    const forgotPassword = renderer.create(
      <BrowserRouter>
        <ForgotPassword />
      </BrowserRouter>
    );

    let tree = forgotPassword.toJSON();

    expect(tree).toMatchSnapshot();
  });
});
