// src/main/components/basilisk-ui-mockup/InsertRequestLogic.js

import React, { useState } from "react";

const API = "http://localhost:8001/insert";

const InsertRequestLogic = ({ onSubmit }) => {
    const [formData, setFormData] = useState({
        name: "",
        data: "",
        tags: [],
        roles: [],
        dataCreator: "user-through-ui",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        var parsedVal = value;
        if (name === "tags" || name === "roles") {
            console.log(parsedVal);
            let splitVal = parsedVal.split(",");
            parsedVal = splitVal;
        }

        console.log(parsedVal);

        setFormData({
            ...formData,
            [name]: parsedVal,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(API, {
                method: "POST",
                mode: "cors", // no-cors, *cors, same-origin
                cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
                credentials: "same-origin", // include, *same-origin, omit
                headers: {
                    "Content-Type": "application/json",
                    "DATA-OWNER": "Marcel",
                },
                redirect: "follow", // manual, *follow, error
                referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
                body: JSON.stringify(formData),
            });
            const result = await response.json();
            onSubmit(result.status); // Pass the result to the parent component (DynamicForm)
        } catch (error) {
            console.error("Error:", error);
        }
    };

    // InsertRequestLogic doesn't return HTML directly
    return {
        formData,
        handleChange,
        handleSubmit,
    };
};

export default InsertRequestLogic;
