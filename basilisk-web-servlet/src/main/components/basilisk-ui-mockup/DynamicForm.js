// src/main/components/basilisk-ui-mockup/DynamicForm.js
import React, { useState } from 'react';
import InsertRequestLogic from './InsertRequestLogic'; // Import the InsertRequestLogic component

const DynamicForm = () => {
  const [formResult, setFormResult] = useState(null); // State to store the form submission result

  const handleFormSubmit = (result) => {
    setFormResult(result); // Update the form submission result in the state
  };

  const { formData, handleChange, handleSubmit } = InsertRequestLogic({ onSubmit: handleFormSubmit });

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label>ID:</label>
          <input type="text" name="ID" value={formData.ID} onChange={handleChange} />
        </div>
        <div>
          <label>Secret:</label>
          <input type="password" name="Secret" value={formData.Secret} onChange={handleChange} />
        </div>
        <button type="submit">Submit</button>
      </form>
      {formResult && <p>Form submitted successfully!</p>} {/* Render a message if form submission was successful */}
    </div>
  );
};

export default DynamicForm;
