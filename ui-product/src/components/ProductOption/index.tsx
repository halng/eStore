import React, { useEffect, useState } from "react";
import Navbar from "../../common/Navbar";
import {
  ProductOptionType,
  ProductOptionCreateType,
} from "../../types/ProductOptionType";
import { toast } from "react-toastify";

import { ProductOptionAPI } from "api-estore-v2";
import Pagination from "../../common/pagination";
import CustomTable from "../../common/CustomTable";

const ProductOption = () => {
  const [options, setOptions] = useState<ProductOptionType[]>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPage, setTotalPage] = useState<number>(3);
  const [totalOptions, setTotalOptions] = useState<number>(0);
  const [newOption, setNewOption] = useState<ProductOptionCreateType>({
    description: "",
    displayType: "",
    name: "",
  });

  const firstRow = [
    { value: "Id", fieldName: "id" },
    { value: "Name", fieldName: "name" },
    { value: "Display Type", fieldName: "displayType" },
    { value: "Description", fieldName: "description" },
    { value: "Create Date", fieldName: "createdDate" },
    { value: "Update Date", fieldName: "updatedDate" },
    { value: "Action", fieldName: "" },
  ];

  const getAllOptions = () => {
    ProductOptionAPI.getAll(page)
      .then((res) => {
        setOptions(res.data.options);
        setTotalPage(res.data.totalPages);
        setTotalOptions(res.data.totalOptions);
      })
      .catch((err) => {
        toast.error("Can't get all group! Try again later.");
      });
  };

  useEffect(() => {
    getAllOptions();
  }, [page]);

  const deleteOption = (e: any, optionId: string) => {
    ProductOptionAPI.delete(optionId)
      .then((res) => {
        toast.success(res.data.message);
        getAllOptions();
      })
      .catch((err) => {
        toast.error(err.response.data.msg);
      });
  };

  const updateOption = (
    e: any,
    data: ProductOptionCreateType,
    option: ProductOptionType
  ) => {
    if (!data.description) {
      data.description = option.description;
    }
    if (!data.displayType) {
      data.displayType = option.displayType;
    }
    if (!data.name) {
      data.name = option.name;
    }
    ProductOptionAPI.update(data, option.id)
      .then((res) => {
        toast.success(res.data.message);
        getAllOptions();
      })
      .catch((err) => {
        console.log(err);
        toast.error("Cannot Update. Try later");
      });
  };

  const createOption = () => {
    ProductOptionAPI.create(newOption)
      .then((res) => {
        toast.success(res.data.message);
        getAllOptions();
        setNewOption({
          name: "",
          displayType: "",
          description: "",
        });
      })
      .catch((err) => {
        toast.error("Create new group failed. Try again later!");
      });
  };

  return (
    <div className="product-option">
      <Navbar />

      <div className="product-option-main">
        <div className="d-flex justify-content-between pb-3">
          <div className="fs-3">Product Option</div>
          <div className="product-option-func d-flex justify-content-end align-items-center">
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
                placeholder="Search in option name"
                aria-label="Search"
                // onChange={(e) => onSearchHandler(e)}
              />
            </div>

            <button
              type="button"
              className="btn btn-primary"
              data-bs-toggle="offcanvas"
              data-bs-target="#offcanvasRight"
              aria-controls="offcanvasRight"
            >
              Create new option
            </button>

            <div
              className="offcanvas offcanvas-end"
              tabIndex={-1}
              id="offcanvasRight"
              aria-labelledby="offcanvasRightLabel"
            >
              <div className="offcanvas-header">
                <h5 className="offcanvas-title" id="offcanvasRightLabel">
                  Create New Option:
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="offcanvas"
                  aria-label="Close"
                ></button>
              </div>
              <div className="offcanvas-body d-flex flex-column align-content-center">
                <div>
                  <input
                    type="text"
                    className="form-control"
                    id="new-option-name"
                    value={newOption.name}
                    placeholder="New option name"
                    onChange={(e) =>
                      setNewOption({ ...newOption, name: e.target.value })
                    }
                  />
                </div>
                <select
                  className="form-select mt-3"
                  onChange={(e) =>
                    setNewOption({ ...newOption, displayType: e.target.value })
                  }
                >
                  <option defaultValue={newOption.displayType} hidden>
                    Select Display Type Of Option
                  </option>
                  <option value="color">Color</option>
                  <option value="number">Number</option>
                  <option value="text">Text</option>
                </select>
                <div className="mt-3">
                  <textarea
                    className="form-control"
                    placeholder="Description for new options"
                    rows={4}
                    value={newOption.description}
                    onChange={(e) =>
                      setNewOption({
                        ...newOption,
                        description: e.target.value,
                      })
                    }
                  />
                </div>
                <div className="pt-3 text-center">
                  <button
                    type="button"
                    className="btn btn-primary"
                    data-bs-dismiss="offcanvas"
                    onClick={createOption}
                  >
                    Create
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <CustomTable
          category={"option"}
          firstRow={firstRow}
          rows={options}
          onDeleteHandler={deleteOption}
          onUpdateHandler={updateOption}
        ></CustomTable>

        <Pagination
          total={totalOptions}
          currentPage={page}
          totalPage={totalPage}
          setPage={setPage}
        />
      </div>
    </div>
  );
};

export default ProductOption;
