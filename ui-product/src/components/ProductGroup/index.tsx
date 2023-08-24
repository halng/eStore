import React, { useEffect, useState } from "react";
import Navbar from "../../common/Navbar";
import { ProductGroupType } from "../../types/ProductGroupType";
import { toast } from "react-toastify";

import { ProductGroupAPI } from "api-estore-v2";

const ProductGroup = () => {
  const [groups, setGroups] = useState<ProductGroupType[]>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPage, setTotalPage] = useState<number>(1);
  const [totalGroups, setTotalGroups] = useState<number>(0);
  const [newGroup, setNewGroup] = useState<string>("");
  const firstRow = [
    "Id",
    "Name",
    "Status",
    "Create Date",
    "Update Date",
    "Actions",
  ];

  const getAllGroup = () => {
    ProductGroupAPI.getAll(page)
      .then((res) => {
        setGroups(res.data.groups);
        setTotalPage(res.data.totalPages);
        setTotalGroups(res.data.totalGroup);
      })
      .catch((err) => {
        toast.error("Can't get all group! Try again later.");
      });
  };

  useEffect(() => {
    getAllGroup();
  }, [page]);

  const deleteGroup = (e: any, groupId: number) => {
    ProductGroupAPI.deleteGroup(groupId)
      .then((res) => {
        toast.success(res.data.message);
        getAllGroup();
      })
      .catch((err) => {
        toast.error(err.response.data.msg);
      });
  };

  const updateGroup = (e: any, groupName: string, groupId: number) => {
    ProductGroupAPI.update(groupName, groupId)
      .then((res) => {
        toast.success(res.data.message);
        getAllGroup();
      })
      .catch((err) => {
        console.log(err);
        toast.error("Cannot Update. Try later");
      });
  };

  const createGroup = (e: any) => {
    ProductGroupAPI.create(newGroup)
      .then((res) => {
        toast.success(res.data.message);
        getAllGroup();
        setNewGroup("");
      })
      .catch((err) => {
        toast.error("Create new group failed. Try again later!");
      });
  };

  const changeStatus = (e: any, groupId: number, status: string) => {
    if (status === "ENABLED") {
      ProductGroupAPI.disableGroup(groupId)
        .then((res) => {
          toast.success(res.data.message);
          getAllGroup();
        })
        .catch((err) => {
          toast.error("Disable group failed. Try again later!");
        });
    } else {
      ProductGroupAPI.enabledGroup(groupId)
        .then((res) => {
          toast.success(res.data.message);
          getAllGroup();
        })
        .catch((err) => {
          toast.error("Enable group failed. Try again later!");
        });
    }
  };

  const onSearchHandler = (e: any) => {
    console.log(e.target.value);
    const tempGroups = groups.filter((item) =>
      item.name.includes(e.target.value)
    );
    setGroups(tempGroups);
  };

  const DeleteGroupModal = ({ groupName, groupId }: any) => {
    return (
      <div>
        {/* <!-- Button trigger modal --> */}
        <button
          type="button"
          className="btn btn-danger"
          data-bs-toggle="modal"
          data-bs-target={`#deleteModal_${groupId}`}
        >
          Delete
        </button>

        {/* <!-- Modal --> */}
        <div
          className="modal fade"
          id={`deleteModal_${groupId}`}
          data-bs-keyboard="false"
          tabIndex={-1}
          aria-labelledby="staticBackdropLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h1 className="modal-title fs-5" id="staticBackdropLabel">
                  Delete group
                </h1>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body">
                Do you want to delete group name:
                <span className="text-danger"> {groupName}</span>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-danger"
                  data-bs-dismiss="modal"
                  onClick={(e) => deleteGroup(e, groupId)}
                >
                  Delete
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const DisableEnableGroupModal = ({ groupName, groupId, status }: any) => {
    return (
      <div>
        {/* <!-- Button trigger modal --> */}
        <button
          type="button"
          className="btn btn-warning"
          data-bs-toggle="modal"
          data-bs-target={`#updateStatus${groupId}`}
        >
          {status === "DISABLED" ? "Enable" : "Disable"}
        </button>

        {/* <!-- Modal --> */}
        <div
          className="modal fade"
          id={`updateStatus${groupId}`}
          data-bs-keyboard="false"
          tabIndex={-1}
          aria-labelledby="staticBackdropLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h1 className="modal-title fs-5" id="staticBackdropLabel">
                  {status === "DISABLED" ? "Enable" : "Disable"} Group
                </h1>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body">
                Do you want to {status === "DISABLED" ? "Enable" : "Disable"}{" "}
                group name:
                <span className="text-danger"> {groupName}</span>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-danger"
                  data-bs-dismiss="modal"
                  onClick={(e) => changeStatus(e, groupId, status)}
                >
                  Change
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };

  const UpdateGroupModal = ({ groupName, groupId }: any) => {
    const [newName, setNewName] = useState<string>(groupName);
    return (
      <div>
        {/* <!-- Button trigger modal --> */}
        <button
          type="button"
          className="btn btn-primary"
          data-bs-toggle="modal"
          data-bs-target={`#updateModal_${groupId}`}
        >
          Update
        </button>

        {/* <!-- Modal --> */}
        <div
          className="modal fade"
          id={`updateModal_${groupId}`}
          data-bs-keyboard="false"
          tabIndex={-1}
          aria-labelledby="staticBackdropLabel"
          aria-hidden="true"
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h1 className="modal-title fs-5" id="staticBackdropLabel">
                  Update Group
                </h1>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div className="modal-body text-start">
                <div className="mb-3">
                  <p className="fs-5">Old name: {groupName}</p>
                  <label htmlFor="new-group-name" className="col-form-label">
                    New Group Name:
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="new-group-name"
                    value={newName}
                    onChange={(e) => setNewName(e.target.value)}
                  />
                </div>
              </div>
              <div className="modal-footer">
                <button
                  type="button"
                  className="btn btn-primary"
                  data-bs-dismiss="modal"
                  onClick={(e) => updateGroup(e, newName, groupId)}
                >
                  Update
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="product-group">
      <Navbar />

      <div className="product-group-main">
        <div className="d-flex justify-content-between pb-3">
          <div className="fs-3">Product Group</div>
          <div className="product-group-func d-flex justify-content-end align-items-center">
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
                onChange={(e) => onSearchHandler(e)}
              />
            </div>

            <button
              type="button"
              className="btn btn-primary"
              data-bs-toggle="offcanvas"
              data-bs-target="#offcanvasRight"
              aria-controls="offcanvasRight"
            >
              Create new group
            </button>

            <div
              className="offcanvas offcanvas-end"
              tabIndex={-1}
              id="offcanvasRight"
              aria-labelledby="offcanvasRightLabel"
            >
              <div className="offcanvas-header">
                <h5 className="offcanvas-title" id="offcanvasRightLabel">
                  Create New Group:
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
                  <label htmlFor="new-group-name" className="col-form-label">
                    New Group Name:
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="new-group-name"
                    value={newGroup}
                    onFocus={(e) => setNewGroup(e.target.value)}
                  />
                </div>
                <div className="pt-3 text-center">
                  <button
                    type="button"
                    className="btn btn-primary"
                    data-bs-dismiss="offcanvas"
                    onClick={createGroup}
                  >
                    Create
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="table-responsive">
          <table className="table">
            <thead className="table-light">
              <tr>
                {(firstRow || []).map((item, index) => (
                  <th key={index} scope="col">
                    {item}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {(groups || []).map((item) => (
                <tr key={item.id}>
                  <th scope="row">{item.id}</th>
                  <td>{item.name}</td>
                  <td
                    className={
                      item.status === "REMOVED"
                        ? "text-danger"
                        : item.status === "DISABLED"
                        ? "text-warning"
                        : "text-success"
                    }
                  >
                    {item.status}
                  </td>
                  <td>{item.createdDate}</td>
                  <td>{item.updatedDate}</td>
                  {item.status !== "REMOVED" && (
                    <td className="d-flex flex-row justify-content-center">
                      <UpdateGroupModal
                        groupName={item.name}
                        groupId={item.id}
                      />

                      <p className="px-3"></p>
                      <DisableEnableGroupModal
                        groupName={item.name}
                        groupId={item.id}
                        status={item.status}
                      />
                      <p className="px-3"></p>
                      <DeleteGroupModal
                        groupName={item.name}
                        groupId={item.id}
                      />
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="d-flex justify-content-between align-items-center">
          <div className="summary">Total: {totalGroups} Groups</div>
          <div className="pagination">
            <nav aria-label="...">
              <ul className="pagination">
                <li className={page === 1 ? "page-item disabled" : "page-item"}>
                  <a
                    role="button"
                    className="page-link"
                    onClick={() => setPage(page - 1)}
                  >
                    Previous
                  </a>
                </li>
                {[...Array(totalPage)].map((item, index) => (
                  <li className="page-item" key={index}>
                    <a
                      role="button"
                      onClick={() => setPage(index + 1)}
                      className={
                        page === index + 1 ? "page-link active" : "page-link"
                      }
                    >
                      {index + 1}
                    </a>
                  </li>
                ))}
                <li
                  className={
                    totalPage === page ? "page-item disabled" : "page-item"
                  }
                >
                  <a
                    role="button"
                    className="page-link"
                    onClick={() => setPage(page + 1)}
                  >
                    Next
                  </a>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductGroup;
