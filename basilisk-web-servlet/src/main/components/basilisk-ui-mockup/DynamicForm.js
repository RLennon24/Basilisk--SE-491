// src/main/components/basilisk-ui-mockup/DynamicForm.js

import React, { useState } from 'react';

const DynamicForm = () => {
  const [formFields, setFormFields] = useState([
    { name: 'ID', label: 'ID', type: 'text', value: '' },
    { name: 'Secret', label: 'Secret', type: 'password', value: '' },
  ]);

  const handleChange = (e, index) => {
    const { name, value } = e.target;
    const newFormFields = [...formFields];
    newFormFields[index].value = value;
    setFormFields(newFormFields);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = formFields.reduce((acc, field) => {
      acc[field.name] = field.value;
      return acc;
    }, {});
    try {
      const response = await fetch('/api/insert', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });
      const result = await response.json();
      console.log('Success:', result);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        {formFields.map((field, index) => (
          <div key={index}>
            <label>{field.label}:</label>
            <input
              type={field.type}
              name={field.name}
              value={field.value}
              onChange={(e) => handleChange(e, index)}
            />
          </div>
        ))}
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default DynamicForm;
