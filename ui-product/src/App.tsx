import React from "react";
import ReactDOM from "react-dom";
import { Route, Routes } from "react-router-dom";

import "./index.css";
import { Product, ProductAttribute, ProductGroup } from "./components";

const App = () => (
  <div className="">
    <Routes>
      <Route path="all" element={<Product />}></Route>
      <Route path="attribute" element={<ProductAttribute />}></Route>
      <Route path="group" element={<ProductGroup />}></Route>
    </Routes>
  </div>
);

export default App;
// ReactDOM.render(<App />, document.getElementById("app"));
