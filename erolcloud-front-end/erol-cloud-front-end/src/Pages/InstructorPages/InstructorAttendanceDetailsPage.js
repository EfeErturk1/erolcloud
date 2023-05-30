import React, {useState, useEffect} from 'react'
import {useParams, useNavigate} from 'react-router-dom';
import '../StudentPages/StudentAttendanceDetails.css'

const InstructorAttendanceDetailsPage = () => {
    let navigate = useNavigate()
    const {courseId} = useParams()
    const [course, setCourse] = useState(null)

    useEffect( () => {
        const fetchCourse = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/courses/${courseId}`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })

                const data = await response.json()
                if (response.ok) {
                    console.log('data: ' + data)
                    setCourse(data)
                }
            } catch (e) {
                console.log('Error fetching course:', e)
            }
        }

        fetchCourse()
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
                        <strong>Number of students:</strong> TODO
                    </div>
                    <div className='course-info'>
                        <strong>Time slots:</strong> TODO
                    </div>
                </div>
                <div className='course-info-container attendance-info-container'>
                    <h2>Attendance Information</h2>
                    TODO
                </div>
            </main>
        </div>
    )
}

export default InstructorAttendanceDetailsPage