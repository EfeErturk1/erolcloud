import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {Login, SignUp, Buffer} from "./Auth";
import {StudentHomePage, StudentAttend, StudentAttendancesPage, StudentCoursePage, InstructorHomePage} from "./Pages";
import InstructorTakeAttendance from "./Pages/InstructorPages/InstructorTakeAttendancePage";

function App() {
    if (!localStorage.getItem('token')) {
        return (
            <div>
                <Router>
                    <Routes>
                        <Route path="/signup" element={<SignUp/>}/>
                        <Route path="/" element={<Login/>}/>
                        <Route path="redirecting" element={<Buffer/>}/>
                    </Routes>
                </Router>
            </div>
        );
    } else if (localStorage.role === "ADMIN") {
        return (
            <div>
                <h1>ROLE_ADMIN</h1>
                {/* Render your admin-specific routes or components here */}
            </div>
        );
    } else if (localStorage.role === "STUDENT") {
        return (
            <div>
                <div>
                    <Router>
                        <Routes>
                            <Route path="/" element={<StudentHomePage/>}/>
                            <Route path="/attend" element={<StudentAttend/>}/>
                            <Route path='/enroll' element={<StudentCoursePage/>}/>
                            <Route path='/attendances' element={<StudentAttendancesPage/>}/>
                        </Routes>
                    </Router>
                </div>
            </div>
        );
    } else if (localStorage.role === "INSTRUCTOR") {
        return (
            <div>
                <div>
                    <div>
                        <Router>
                            <Routes>
                                <Route path="/" element={<InstructorHomePage/>}/>
                                <Route path="/take-attendance" element={<InstructorTakeAttendance/>}/>
                                <Route path='/view-attendances' element={<StudentCoursePage/>}/>
                            </Routes>
                        </Router>
                    </div>
                </div>
            </div>
        );
    }

    // If no condition is met, you can handle the default case here
    return null;
}

export default App;
