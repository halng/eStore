import React from "react";
import ReactDOM from "react-dom";
import Test from 'ui-auth/test'
import "./index.css";

const App = () => (
  <div className="container">
   hello from ui-home
   <Test />
  </div>
);
ReactDOM.render(<App />, document.getElementById("app"));
