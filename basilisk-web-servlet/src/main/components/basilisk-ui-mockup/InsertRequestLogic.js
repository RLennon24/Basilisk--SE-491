// src/main/components/basilisk-ui-mockup/InsertRequestLogic.js

import React, { useState } from 'react';

const InsertRequestLogic = () => {
  const [formData, setFormData] = useState({
    ID: '',
    secret: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
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
        <div>
          <label>ID:</label>
          <input type="text" name="ID" value={formData.ID} onChange={handleChange} />
        </div>
        <div>
          <label>Secret:</label>
          <input type="password" name="Secret" value={formData.password} onChange={handleChange} />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default InsertRequestLogic;
