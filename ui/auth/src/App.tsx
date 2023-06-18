import React from "react";
import ReactDOM from "react-dom";

import "./index.scss";
import Register from "./pages/Register";
import LogIn from "./pages/Login";
import ForgotPassword from "./pages/ForgotPassword";

const App = () => (
  <div className="mt-10 text-3xl mx-auto max-w-6xl">
    <ForgotPassword />
  </div>
);
ReactDOM.render(<App />, document.getElementById("app"));
