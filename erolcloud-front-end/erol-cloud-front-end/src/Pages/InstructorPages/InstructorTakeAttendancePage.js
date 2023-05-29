import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
//import './Page.css';

const InstructorTakeAttendance = () => {
    let navigate = useNavigate();
    const [code, setCode] = useState("LOLO No code exists");
    const instructorId = localStorage.getItem("id");

    useEffect(() => {
    const displayCode = async () => {
        try {
            const response = fetch("http://localhost:8080/api/v1/instructors/" + instructorId + "/lectures/current", {
                method: "GET",
                headers: {
                    "Content-type": "application/json",
                    "Accept": "application/json",
                    "Authorization": "Bearer " + localStorage.getItem('token')
                }
            })
            const data = await response.json();
            setCode(data)
            console.log(data);
        } catch (e) {
            console.log("Error fetching courses:", e);
            setCode("Error in code display")
        }
    };
        displayCode();
        }, []);


    return (
        <div className="container">
            <div className="row mt-4">
                <div className="col-12">
                    <button className="btn btn-primary" onClick={() => navigate('/')}>Home</button>
                </div>
            </div>
            <div className="row mt-4">
                <div className="col-12 text-center">
                    <h2>To register attendance, enter the code that is displayed below</h2>
                </div>
            </div>
            <div className="row mt-4">
                <div className="col-12 text-center">
                    <h2>{code}</h2>
                </div>
            </div>
        </div>
    );
};

export default InstructorTakeAttendance;
