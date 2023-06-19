import React from "react";
import ReactDOM from "react-dom";

import "./index.scss";
import Header from "./components/Header";
import Footer from "./components/Footer";

const App = () => {


  return (
    <div className="mt-10 text-3xl mx-auto max-w-6xl">
      <Header />
      <Footer />
    </div>
  );
};
ReactDOM.render(<App />, document.getElementById("app"));
