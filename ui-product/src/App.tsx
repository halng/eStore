import React from "react";
import ReactDOM from "react-dom";
import { Route, Routes } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

import "react-toastify/dist/ReactToastify.css";
import "./index.css";
import {
  Product,
  ProductAttribute,
  ProductGroup,
  ProductOption,
} from "./components";

const App = () => (
  <div className="">
    <Routes>
      <Route path="all" element={<Product />}></Route>
      <Route path="option" element={<ProductOption />}></Route>
      <Route path="attribute" element={<ProductAttribute />}></Route>
      <Route path="group" element={<ProductGroup />}></Route>
    </Routes>
    <ToastContainer />
  </div>
);

export default App;
// ReactDOM.render(<App />, document.getElementById("app"));
