import './App.css';
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import AddUser from './comp/AddUser';
import UserList from './comp/UserList';
import NavBar from './comp/NavBar';
import HomePage from './comp/HomePage';
import Login from './comp/Login'

function App() {
  return (
    <>
      <NavBar />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/register" element={<AddUser />} />
        <Route path="/login" element={<Login />} />
        <Route path="/users" element={<UserList />} />
      </Routes>
    </>
  );
}

export default App;
