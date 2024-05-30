// src/main/components/basilisk-ui-mockup/DynamicForm.js
import { Fragment, useState } from "react";
import InsertRequestLogic from "../logic/InsertRequestLogic"; // Import the InsertRequestLogic component

export default function DynamicForm() {
  const [status, setStatus] = useState(""); // State to store the form submission result

  const handleFormSubmit = (result) => {
    setStatus(result); // Update the form submission result in the state
  };

  function generateStatusBox() {
    if (status === "Success") {
      return (
        <Fragment>
          <div className="statusSuccess">
            <p>Success</p>
          </div>
        </Fragment>
      );
    } else {
      return (
        <Fragment>
          <div className="statusError">
            <p>{status}</p>
          </div>
        </Fragment>
      );
    }
  }

  const { formData, handleChange, handleSubmit } = InsertRequestLogic({
    onSubmit: handleFormSubmit,
  });

  return (
    <div className="holder">
      <form onSubmit={handleSubmit}>
        <h2>Insert Your Data</h2>
        <div>
          <label>Name:</label>
          <p />
          <input
            type="text"
            name="name"
            placeholder="Enter a brief summary of Data"
            size={53}
            value={formData.name}
            onChange={handleChange}
          />
          <p />
        </div>
        <div>
          <label>Data:</label>
          <p />
          <textarea
            name="data"
            placeholder="Enter your Data here"
            rows="4"
            cols="50"
            value={formData.data}
            onChange={handleChange}
          />
          <p />
        </div>
        <div>
          <label>Tags:</label>
          <p />
          <input
            name="tags"
            placeholder="Optionally Tag your data"
            value={formData.tags}
            onChange={handleChange}
          />
          <p />
        </div>
        <div>
          <label>Roles:</label>
          <p />
          <input
            name="roles"
            placeholder="Optionally add Existing Roles your data"
            value={formData.roles}
            onChange={handleChange}
          />
        </div>
        <p />
        <button type="submit">Submit</button>
        {status && generateStatusBox()}
      </form>
    </div>
  );
}
