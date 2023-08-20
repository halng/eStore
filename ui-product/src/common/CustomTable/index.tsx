import React from "react";
import "./style.css";
import { ProductGroupType } from "../../types/ProductGroupType";

interface propsData {
  firstRow: string[];
  rows: ProductGroupType[] | [];
  updateFunc: JSX.Element;
  deleteFunc: any;
}

const CustomTable = ({ firstRow, rows, updateFunc, deleteFunc }: propsData) => {
  return (
    <div className="table-responsive">
      <table className="table">
        <thead className="table-light">
          <tr>
            {(firstRow || []).map((item) => (
              <th scope="col">{item}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {(rows || []).map((item) => (
            <tr>
              <th scope="row">{item.id}</th>
              <td>{item.name}</td>
              <td>{item.createdDate}</td>
              <td>{item.updateDate}</td>
              <td className="d-flex flex-row justify-content-center">
                <updateFunc />
                <button
                  type="button"
                  className="btn btn-danger pe-3"
                  onClick={(e) => deleteFunc(e,item.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CustomTable;
