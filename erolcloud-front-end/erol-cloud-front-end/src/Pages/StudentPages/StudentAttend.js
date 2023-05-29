import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";
//import './Page.css';

const StudentAttend = () => {
    let navigate = useNavigate();
    const [code, setCode] = useState("");
    const studentId = localStorage.getItem("id");
    const handleRegister = () => {
        if (code.length !== 6) {
            window.alert("Please enter a valid code");
        } else {
            fetch("http://localhost:8080/api/v1/students/"+studentId+"/attendCurrentLecture/"+code, {
                method: "PUT",
                headers: {
                    "Content-type": "application/json",
                    "Accept": "application/json",
                    "Authorization": "Bearer " + localStorage.getItem('token')
                }
            }).then((r) => {
                if (r.status === 200) {
                    window.alert("Attendance registered successfully");
                    navigate('/');
                }else if (r.status === 204){
                    window.alert("You do not have a current lecture to attend");
                    navigate('/');
                }else if (r.status === 400){
                    window.alert("Error in registering attendance");
                }
                else if (r.status === 401 || r.status === 403 || r.status === 500) {
                    return Promise.reject(new Error("hata oluştu"));
                } else {
                    return Promise.reject(new Error("bilinmeyen hata"));
                }
            })
        }
    };

    return (
        <div className="container">
            <div className="row mt-4">
                <div className="col-12">
                    <button className="btn btn-primary" onClick={() => navigate('/')}>Home</button>
                </div>
            </div>
            <div className="row mt-4">
                <div className="col-12 text-center">
                    <h2>You are currently having X course</h2>
                    <h2>To register attendance, enter the code that your instructor has displayed below</h2>
                </div>
            </div>
            <div className="row mt-4">
                <div className="col-12 text-center">
                    <input type="text" maxLength="6" className="form-control" placeholder="Enter the 6-digit Attendance code"
                           onChange={
                        e => setCode(e.target.value)
                    }/>
                </div>
            </div>
            <div className="row mt-4">
                <div className="col-12 text-center">
                    <button onClick={handleRegister} className="btn btn-primary">Register</button>
                </div>
            </div>
        </div>
    );
};

export default StudentAttend;
