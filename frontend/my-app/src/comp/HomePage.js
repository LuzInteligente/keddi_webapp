import React from "react";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import "./HomePage.css";

const HomePage = () => {
  const navigate = useNavigate();

  return (
    <div className="homepage-background">
  <div className="homepage-content">
    <h1 className="homepage-title">Welcome to the Budget Neptun</h1>

    <div className="homepage-buttons">
      <Button
        variant="contained"
        className="homepage-button"
        onClick={() => navigate("/register")}
      >
        Join
      </Button>
      <Button
        variant="contained"
        className="homepage-button"
        onClick={() => navigate("/login")}
      >
        Log in
      </Button>
    </div>
  </div>
</div>
  );
};

export default HomePage;
