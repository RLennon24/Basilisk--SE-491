// src/main/components/basilisk-ui-mockup/AssignRolesForm.js
import React, { useState, useEffect } from 'react';

const AssignRolesForm = () => {
    const [users, setUsers] = useState([]);
    const [roles, setRoles] = useState([]);
    const [selectedUser, setSelectedUser] = useState('');
    const [selectedRoles, setSelectedRoles] = useState([]);
    const [message, setMessage] = useState('');

    useEffect(() => {
        // Fetch users and roles from the server
        const fetchData = async () => {
            try {
                const usersResponse = await fetch('http://localhost:8001/api/users');
                const rolesResponse = await fetch('http://localhost:8001/api/roles');
                const usersData = await usersResponse.json();
                const rolesData = await rolesResponse.json();
                setUsers(usersData);
                setRoles(rolesData);
            } catch (error) {
                console.error('Error fetching data', error);
            }
        };
        fetchData();
    }, []);

    const handleUserChange = (e) => {
        setSelectedUser(e.target.value);
    };

    const handleRoleChange = (role) => {
        setSelectedRoles((prevSelectedRoles) =>
            prevSelectedRoles.includes(role)
                ? prevSelectedRoles.filter((r) => r !== role)
                : [...prevSelectedRoles, role]
        );
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8001/api/assign-roles', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    userId: selectedUser,
                    roles: selectedRoles
                })
            });
            if (response.ok) {
                setMessage('Roles assigned successfully!');
            } else {
                setMessage('Error assigning roles');
            }
        } catch (error) {
            setMessage('Error assigning roles');
            console.error('Error:', error);
        }
    };

    return (
        <div>
            <h2>Assign Roles to User</h2>
            {message && <p>{message}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="user">Select User:</label>
                    <select id="user" value={selectedUser} onChange={handleUserChange}>
                        <option value="">--Select a User--</option>
                        {users.map((user) => (
                            <option key={user.id} value={user.id}>
                                {user.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div>
                    <label>Assign Roles:</label>
                    {roles.map((role) => (
                        <div key={role.id}>
                            <input
                                type="checkbox"
                                id={`role-${role.id}`}
                                value={role.id}
                                checked={selectedRoles.includes(role.id)}
                                onChange={() => handleRoleChange(role.id)}
                            />
                            <label htmlFor={`role-${role.id}`}>{role.name}</label>
                        </div>
                    ))}
                </div>
                <button type="submit">Assign Roles</button>
            </form>
        </div>
    );
};

export default AssignRolesForm;
