import React from 'react';
import {useNavigate} from "react-router-dom";
import './StudentHomePage.css';

const StudentHomePage = () => {
    let navigate = useNavigate();
    console.log('token:'+ localStorage.getItem('token'));
    const studentName = localStorage.getItem('name');

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
                <h3 style={{ padding: '20px' }}>Welcome {studentName}</h3>
                <button onClick={() => navigate('/attend')} className="btn btn-primary mb-3">Attend lecture</button>
                <button onClick={() => navigate('/attendances')} className="btn btn-primary mb-3">View attendances</button>
                <button onClick={() => navigate('/enroll')} className="btn btn-primary mb-3">Enroll in courses</button>
            </main>
        </div>
    );
};

export default StudentHomePage;
