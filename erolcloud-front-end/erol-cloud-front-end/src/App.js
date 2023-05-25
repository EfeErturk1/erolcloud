import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Login, SignUp, Buffer } from "./Auth";

function App() {
  if (!localStorage.getItem('token')) {
    return (
      <div>
        <Router>
          <Routes>
            <Route path="/signup" element={<SignUp />} />
            <Route path="/" element={<Login />} />
            <Route path="redirecting" element={<Buffer />} />
          </Routes>
        </Router>
      </div>
    );
  } else if (localStorage.role === "ROLE_ADMIN") {
    return (
      <div>
        <h1>ROLE_ADMIN</h1>
        {/* Render your admin-specific routes or components here */}
      </div>
    );
  } else if (localStorage.role === "ROLE_STUDENT") {
    return (
      <div>
        <h1>ROLE_STUDENT</h1>
        {/* Render your student-specific routes or components here */}
      </div>
    );
  } else if (localStorage.role === "ROLE_INSTRUCTOR") {
    return (
      <div>
        <h1>ROLE_INSTRUCTOR</h1>
        {/* Render your instructor-specific routes or components here */}
      </div>
    );
  }

  // If no condition is met, you can handle the default case here
  return null;
}

export default App;
