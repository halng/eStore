import React, { useEffect, useState } from "react";
import Navbar from "../../common/Navbar";
import { ProductGroupType } from "../../types/ProductGroupType";

const ProductGroup = () => {
  const [groups, setGroups] = useState<ProductGroupType[]>([]);
  useEffect(() => {
    setGroups([
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 2,
        name: "group 2",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 3,
        name: "group 3",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 4,
        name: "group 4",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 5,
        name: "group 5",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 6,
        name: "group 6",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 7,
        name: "group 7",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 8,
        name: "group 8",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 9,
        name: "group 9",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 10,
        name: "group 10",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 11,
        name: "group 11",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
      {
        id: 1,
        name: "group 1",
        createdDate: "2023-01-01",
        updateDate: "2023-01-13",
      },
    ]);
  }, []);
  return (
    <div className="product-group">
      <Navbar />

      <div className="product-group-main">
        <div className="d-flex justify-content-between pb-3">
          <div className="fs-3">Product Group</div>
          <div className="product-group-func d-flex justify-content-end">
            <div className="search-box d-flex align-items-center me-3">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="18"
                height="18"
                fill="currentColor"
                className="bi bi-search"
                viewBox="0 0 16 16"
              >
                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
              </svg>
              <input
                className="me-2 ps-3 py-2"
                type="text"
                placeholder="Search in group name"
                aria-label="Search"
              />
            </div>

            <button type="button" className="btn btn-primary">
              Create new group
            </button>
          </div>
        </div>
        <div className="table-responsive">
          <table className="table">
            <thead className="table-light">
              <tr>
                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col">Create Date</th>
                <th scope="col">Update Date</th>
                <th scope="col">Action</th>
              </tr>
            </thead>
            <tbody>
              {(groups || []).map((item) => (
                <tr>
                  <th scope="row">{item.id}</th>
                  <td>{item.name}</td>
                  <td>{item.createdDate}</td>
                  <td>{item.updateDate}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default ProductGroup;
