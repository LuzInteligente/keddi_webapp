import React from "react";
import {
    AppBar, Toolbar, Typography, IconButton, Box, Tooltip
} from "@mui/material";
import { Person, Logout } from "@mui/icons-material";
import { useNavigate, useLocation } from "react-router-dom";
import styles from "./Navbar.module.css";

const NavBar = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const handleLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        navigate("/");
    };

    const isOnUsersPage = location.pathname === "/users";

    return (
        <AppBar position="sticky" className={styles.appBar}>
            <Toolbar className={styles.toolbar}>
                <Box className={styles.leftSection}>
                    <Typography
                        variant="h6"
                        className={styles.logo}
                        onClick={() => navigate("/")}
                    >
                        Budget Neptun
                    </Typography>
                </Box>

                <Box className={styles.rightSection}>
                    {isOnUsersPage && (
                        <Tooltip title="Kijelentkezés">
                            <IconButton onClick={handleLogout} className={styles.iconButton}>
                                <Logout sx={{ color: "#fff", fontSize: 26 }} />
                            </IconButton>
                        </Tooltip>
                    )}
                    <IconButton onClick={() => navigate("/register")} className={styles.iconButton}>
                        <Person sx={{ color: "#fff", fontSize: 26 }} />
                    </IconButton>
                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default NavBar;
