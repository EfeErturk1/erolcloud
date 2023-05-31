import React, {useState, useEffect} from 'react'
import {useParams, useNavigate} from 'react-router-dom';
import '../StudentPages/StudentAttendanceDetails.css'

const InstructorAttendanceDetailsPage = () => {
    let navigate = useNavigate()
    const {courseId} = useParams()
    const [course, setCourse] = useState(null)
    const [lectures, setLectures] = useState([])
    const instructorId = localStorage.getItem('id')
    const [timeSlots, setTimeSlots] = useState([])
    const [noOfStudents, setNoOfStudents] = useState([])

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
                    setCourse(data)
                }
            } catch (e) {
                console.log('Error fetching course:', e)
            }
        }

        fetchCourse()
    }, [])

    useEffect(() => {
        const fetchLectures = async () => {
            try {
                const response = await fetch(`http://erolcloud-back-end.uc.r.appspot.com/api/v1/instructors/${instructorId}/attendances`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })
                const data = await response.json()
                console.log(data)
                if (response.ok) {
                    setLectures(data)
                }
            } catch (e) {
                console.log('Error fetching lectures:', e)
            }
        }

        fetchLectures()


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


        const fetchNoOfStudents = async () => {
            try {
                const response = await fetch(`https://erolcloud-back-end.uc.r.appspot.com/api/v1/instructors/${instructorId}/courses/${courseId}/students`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })

                const data = await response.json()
                if (response.ok) {
                    console.log('no of students: ' + data.length)
                    setNoOfStudents(data.length)
                }
            } catch (e) {
                console.log('Error fetching course:', e)
            }
        }

        fetchNoOfStudents()
    }, [])

    if (!course) {
        return <div>Loading...</div>;
    }



    const viewAttendedStudents = (lectureId) => {
        navigate(`/attended-students/${courseId}/${lectureId}`)
    }

    return (
        <div className='container'>
            <div className="row mt-4">
                <div className="col-12">
                    <button className="btn btn-primary" onClick={() => navigate('/')}>Home</button>
                    <button className="btn btn-primary" onClick={() => navigate('/view-attendances')}>Back</button>
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
                        <strong>Number of students:</strong> {noOfStudents}
                    </div>
                    <div className='course-info'>
                        <strong>Time slots:</strong> {timeSlots}
                    </div>
                </div>
                <div className='lecture-info-container container p-4'>
                    <h2>Lecture Details</h2>
                    {lectures && lectures.lectures && lectures.lectures.length > 0 ? (
                        <ul className="list-group">
                            {lectures.lectures.map((lectureResponse) => {
                                const startDate = new Date(lectureResponse.lecture.lectureStartDate);
                                const endDate = new Date(lectureResponse.lecture.lectureEndDate);
                                return (
                                    <li key={lectureResponse.lecture.id} className="list-group-item">
                                        <div>
                                            <span className="fw-bold">Lecture Date and Time: {startDate.toLocaleDateString()}, {startDate.toLocaleTimeString()} - {endDate.toLocaleTimeString()}</span>
                                        </div>
                                        <table className="table mt-3">
                                            <thead>
                                            <tr>
                                                <th scope="col" style={{ width: '50%' }}>Student Name</th>
                                                <th scope="col">Attended</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {lectureResponse.studentAttendances.map((studentAttendance) => (
                                                <tr key={studentAttendance.student.id}>
                                                    <td>{studentAttendance.student.name}</td>
                                                    <td>{studentAttendance.attended ? "Yes" : "No"}</td>
                                                </tr>
                                            ))}
                                            </tbody>
                                        </table>
                                    </li>
                                );
                            })}
                        </ul>
                    ) : (
                        <p>No lecture data available.</p>
                    )}
                </div>


            </main>
        </div>
    )
}

export default InstructorAttendanceDetailsPage