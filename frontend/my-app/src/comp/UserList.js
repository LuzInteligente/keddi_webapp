import React, { useEffect, useState } from 'react';
import './UserList.css';

export default function UserList() {
    const [user, setUser] = useState([]);
    const [currentRole, setCurrentRole] = useState('');

    useEffect(() => {
        const token = localStorage.getItem("token");
        const role = localStorage.getItem("role");
        setCurrentRole(role);

        fetch("http://localhost:8080/student/getAll", {
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error("Unauthorized");
                }
                return res.json();
            })
            .then(data => setUser(data))
            .catch(err => {
                console.error("Hiba:", err);
                alert("Nincs jogosultság vagy bejelentkezés szükséges.");
            });
    }, []);

    const handleDelete = (id) => {
        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/student/delete/${id}`, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error("Törlés sikertelen");
                }
                setUser(prevUsers => prevUsers.filter(u => u.id !== id));
            })
            .catch(err => {
                console.error("Törlési hiba:", err);
                alert("Nem sikerült törölni a felhasználót.");
            });
    };

    return (
        <div className="userlist-bg">
            <div className="container">
                <h1 className="users-title">
                    <u>Users</u>
                </h1>

                <div className="paper-wrapper">
                    {user.map(user => (
                        <div className="user-card" key={user.id}>
                            ID: {user.id}<br />
                            Name: {user.name}<br />
                            Role: {user.role}<br />
                            {currentRole === "TEACHER" && (
                                <button className="delete-button" onClick={() => handleDelete(user.id)}>
                                    Törlés
                                </button>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}
