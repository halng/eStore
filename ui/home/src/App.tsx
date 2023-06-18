import React from "react";
import ReactDOM from "react-dom";

import "./index.scss";
import Register from "auth/register";

const App = () => (
  <div className="mt-10 text-3xl mx-auto max-w-6xl">
    <Register />
  </div>
);
ReactDOM.render(<App />, document.getElementById("app"));
