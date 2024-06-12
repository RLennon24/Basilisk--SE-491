// src/main/components/basilisk-ui-mockup/InsertRequestLogic.js

import React, { useState } from 'react';

const API = 'http://localhost:8001/insert';

const InsertRequestLogic = ({ onSubmit }) => {
  const [formData, setFormData] = useState({
    ID: '',
    Secret: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(API, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });
      const result = await response.json();
      onSubmit(result); // Pass the result to the parent component (DynamicForm)
    } catch (error) {
      console.error('Error:', error);
    }
  };

  // InsertRequestLogic doesn't return HTML directly
  return {
    formData,
    handleChange,
    handleSubmit
  };
};

export default InsertRequestLogic;
