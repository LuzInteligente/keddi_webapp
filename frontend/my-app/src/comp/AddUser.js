import React, { useState } from 'react';
import {
    Container, Paper, TextField, Button, Select, MenuItem,
    InputLabel, FormControl
} from '@mui/material';
import { makeStyles } from '@mui/styles';
import { useNavigate } from 'react-router-dom';
import './AddUser.css'

const useStyles = makeStyles(() => ({
    root: {
        '& > *': {
            margin: '10px 0',
        },
    },
}));

export default function Student() {
    const navigate = useNavigate();
    const classes = useStyles();
    const paperStyle = { padding: '50px 20px', width: 600, margin: "20px auto" };
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('');;

    const handleClick = (e) => {
        e.preventDefault();
        const student = { name, password, role };
        console.log(student);
        fetch("http://localhost:8080/student/add", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(student)
        }).then(async response => {
            const data = await response.json();

            if (response.ok) {
                localStorage.setItem("token", data.token);
                navigate("/login"); 
            } else {
                alert("Regisztráció sikertelen");
            }
        });
    };

    return (
        <Container style={{ textAlign: 'center' }}>
            <Paper elevation={3} style={paperStyle} className="adduser-paper">
                <h1 style={{ color: "black" }}>User registration</h1>

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
                        variant="outlined"
                        fullWidth
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    <FormControl fullWidth variant="outlined">
                        <InputLabel id="role-label">Role</InputLabel>
                        <Select
                            labelId="role-label"
                            id="role-select"
                            value={role}
                            onChange={(e) => setRole(e.target.value)}
                            label="Role"
                        >
                            <MenuItem value="TEACHER">TEACHER</MenuItem>
                            <MenuItem value="STUDENT">STUDENT</MenuItem>
                        </Select>
                    </FormControl>

                    <Button variant="contained" color="primary" onClick={handleClick}>
                        Register
                    </Button>

                    <p className="adduser-login-text">
                        Already have an account?{" "}
                        <span className="adduser-login-link" onClick={() => navigate("/login")}>
                            Click here to log in
                        </span>
                    </p>
                </form>
            </Paper>
        </Container>
    );
}
