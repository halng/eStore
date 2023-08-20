import React, { useEffect, useState } from "react";
import "./style.css";

const Navbar = () => {
  const [breadcrumbItems, setBreadcrumbItems] = useState<string[]>([]);

  useEffect(() => {
    let paths = window.location.href.split("/").slice(3);
    setBreadcrumbItems(paths);
  }, []);

  return (
    <nav aria-label="breadcrumb">
      <ol className="breadcrumb">
        <li className="breadcrumb-item d-flex align-items-center justify-content-center">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="18"
            height="18"
            fill="currentColor"
            className="bi bi-house-fill"
            viewBox="0 0 18 18"
          >
            <path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5Z" />
            <path d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6Z" />
          </svg>{" "}
          Home
        </li>
        {breadcrumbItems.map((item, index) => (
          <li className={"breadcrumb-item"} key={index}>
            {item.charAt(0).toUpperCase() + item.substring(1)}
          </li>
        ))}
      </ol>
    </nav>
  );
};

export default Navbar;
