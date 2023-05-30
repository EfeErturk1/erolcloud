import React from 'react';
import {useNavigate} from "react-router-dom";
import './InstructorHomePage.css';

const InstructorHomePage = () => {
    let navigate = useNavigate();
    console.log(localStorage.getItem('accessToken'));

    const handleLogout = () => {
        localStorage.clear();//removeItem('accessToken'); // Clear the access token from localStorage
        navigate('/');
        window.location.reload();
    };

    return (
        <div className="container">
            <header className="header d-flex">
                <h1 className='page-title'>Attendance System</h1>
                <button onClick={handleLogout} className="btn btn-primary">Logout</button>
            </header>
            <main className="main d-flex flex-column align-items-center justify-content-center">
                <button onClick={() => navigate('/take-attendance')} className="btn btn-primary mb-3">Take Attendance</button>
                <button onClick={() => navigate('/view-attendances')} className="btn btn-primary mb-3">View attendances</button>
            </main>
        </div>
    );
};

export default InstructorHomePage;
