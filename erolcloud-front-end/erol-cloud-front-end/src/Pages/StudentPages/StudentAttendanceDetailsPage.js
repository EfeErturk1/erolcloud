import React, {useState, useEffect} from 'react'
import {useParams, useNavigate} from 'react-router-dom';
import './StudentAttendanceDetails.css'

const StudentAttendanceDetailsPage = () => {
    let navigate = useNavigate()
    const studentId = localStorage.getItem('id')
    const {courseId} = useParams()
    const [course, setCourse] = useState(null)
    const [timeSlots, setTimeSlots] = useState([])
    const [instructor, setInstructor] = useState(null)
    const [attendances, setAttendances] = useState([])

    useEffect( () => {
        const fetchCourse = async () => {
            try {
                const response = await fetch(`https://erolcloud-back-end.uc.r.appspot.com/api/v1/courses/${courseId}`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })

                const data = await response.json()
                if (response.ok) {
                    console.log('course: ' + data)
                    setCourse(data)
                }
            } catch (e) {
                console.log('Error fetching course:', e)
            }
        }

        fetchCourse()

        const fetchTimeSlots = async () => {
            try {
                const response = await fetch(`https://erolcloud-back-end.uc.r.appspot.com/api/v1/courses/${courseId}/time-slots`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })

                const data = await response.text()
                if (response.ok) {
                    console.log('timeslots: ' + data)
                    setTimeSlots(data)
                }
            } catch (e) {
                console.log('Error fetching course:', e)
            }
        }

        fetchTimeSlots()


        const fetchTInstructor = async () => {
            try {
                const response = await fetch(`https://erolcloud-back-end.uc.r.appspot.com/api/v1/courses/${courseId}/instructor`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })

                const data = await response.json()
                if (response.ok) {
                    console.log('instructor: ' + data.name)
                    setInstructor(data.name)
                }
            } catch (e) {
                console.log('Error fetching course:', e)
            }
        }

        fetchTInstructor()

        const fetchAttendances = async () => {
            try {
                const response = await fetch(`https://erolcloud-back-end.uc.r.appspot.com/api/v1/students/${studentId}/attendances/courses/${courseId}`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })

                const data = await response.json()
                if (response.ok) {
                    console.log('data: ' + data)
                    setAttendances(data)
                }
            } catch (e) {
                console.log('Error fetching course:', e)
            }
        }

        fetchAttendances()

    }, [])

    if (!course) {
        return <div>Loading...</div>;
    }

    return (
        <div className='container'>
            <div className="row mt-4">
                <div className="col-12">
                    <button className="btn btn-primary" onClick={() => navigate('/')}>Home</button>
                    <button className="btn btn-primary" onClick={() => navigate('/attendances')}>Back</button>
                </div>
            </div>
            <main className='main d-flex flex-column align-items-center justify-content-center'>
                <h1>Attendance for course {`${course.code}-${course.section} ${course.name}`}</h1>
                <div className='course-info-container'>
                    <h2>Course Information</h2>
                    <div className='course-info'>
                        <strong>Course code:</strong> {course.code}
                    </div>
                    <div className='course-info'>
                        <strong>Course name:</strong> {course.name}
                    </div>
                    <div className='course-info'>
                        <strong>Section number:</strong> {course.section}
                    </div>
                    <div className='course-info'>
                        <strong>Instructor:</strong> {instructor}
                    </div>
                    <div className='course-info'>
                        <strong>Time slots:</strong> {timeSlots}
                    </div>
                </div>
                <div className='course-info-container attendance-info-container'>
                    <h2>Attendance Information</h2>
                    {attendances && attendances.lectures && attendances.lectures.length > 0 ? (
                        <table className="table">
                            <thead>
                            <tr>
                                <th>Lecture Date</th>
                                <th>Lecture Time</th>
                                <th>Attended</th>
                            </tr>
                            </thead>
                            <tbody>
                            {attendances.lectures.map((studentLecture) => {
                                const startDate = new Date(studentLecture.lecture.lectureStartDate);
                                const endDate = new Date(studentLecture.lecture.lectureEndDate);
                                const startTime = startDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
                                const endTime = endDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
                                return (
                                    <tr key={studentLecture.lecture.id}>
                                        <td>{startDate.toLocaleDateString()}</td>
                                        <td>{`${startTime} - ${endTime}`}</td>
                                        <td>{studentLecture.attended ? "Yes" : "No"}</td>
                                    </tr>
                                );
                            })}
                            </tbody>
                        </table>
                    ) : (
                        <p>No attendance data available.</p>
                    )}
                </div>


            </main>
        </div>
    )
}

export default StudentAttendanceDetailsPage