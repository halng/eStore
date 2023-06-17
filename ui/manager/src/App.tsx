import React from "react";
import ReactDOM from "react-dom";

import "./index.scss";
import Header from "common/Header";
import Footer from "common/Footer";

const App = () => (
  <div className="mt-10 text-3xl mx-auto max-w-6xl">
    <Header />
    <div>Hello from: manager</div>
    <Footer />
  </div>
);
ReactDOM.render(<App />, document.getElementById("app"));
