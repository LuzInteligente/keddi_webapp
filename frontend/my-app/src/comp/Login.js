import React, { useState } from 'react';
import {
    Container, Paper, TextField, Button
} from '@mui/material';
import { makeStyles } from '@mui/styles';
import { useNavigate } from 'react-router-dom';
import './AddUser.css';

const useStyles = makeStyles(() => ({
    root: {
        '& > *': {
            margin: '10px 0',
        },
    },
}));

export default function Login() {
    const navigate = useNavigate();
    const classes = useStyles();
    const paperStyle = { padding: '50px 20px', width: 600, margin: "20px auto" };

    const [name, setName] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = (e) => {
    e.preventDefault();
    const loginData = { name, password };
    console.log("Login attempt:", loginData);

    fetch("http://localhost:8080/student/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(loginData)
    })
        .then(async res => {
            if (!res.ok) {
                const text = await res.text();
                throw new Error(text || "Sikertelen bejelentkezés");
            }
            return await res.json();
        })
        .then(data => {
            localStorage.setItem("token", data.token);

            if (data.role) {
                localStorage.setItem("role", data.role);
            } else {
                try {
                    const payload = JSON.parse(atob(data.token.split('.')[1]));
                    localStorage.setItem("role", payload.role);
                } catch (e) {
                    console.error("Nem sikerült kinyerni a szerepkört:", e);
                }
            }

            navigate("/users");
        })
        .catch(err => {
            console.error("Bejelentkezési hiba:", err.message);
            alert("Bejelentkezés sikertelen: " + err.message);
        });
};

    return (
        <Container style={{ textAlign: 'center' }}>
            <Paper elevation={3} style={paperStyle} className="adduser-paper">
                <h1 style={{ color: "black" }}>User login</h1>

                <form className={classes.root} noValidate autoComplete="off">
                    <TextField
                        label="Name"
                        variant="outlined"
                        fullWidth
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />

                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    <Button variant="contained" color="primary" onClick={handleLogin}>
                        Log In
                    </Button>

                    <p className="adduser-login-text">
                        Don't have an account?{" "}
                        <span className="adduser-login-link" onClick={() => navigate("/register")}>
                            Register here
                        </span>
                    </p>
                </form>
            </Paper>
        </Container>
    );
}
