import React, {useState, useEffect} from 'react'
import {useParams, useNavigate} from 'react-router-dom';
import '../StudentPages/StudentAttendanceDetails.css'

const InstructorAttendanceDetailsPage = () => {
    let navigate = useNavigate()
    const {courseId} = useParams()
    const [course, setCourse] = useState(null)
    const [lectures, setLectures] = useState([])
    const instructorId = localStorage.getItem('id')

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
                const response = await fetch(`http://localhost:8080/api/v1/instructors/${instructorId}/attendances`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })
                const data = await response.json()
                if (response.ok) {
                    setLectures(data.lectures)
                }
            } catch (e) {
                console.log('Error fetching lectures:', e)
            }
        }

        fetchLectures()
    }, [])

    if (!course) {
        return <div>Loading...</div>;
    }

    const filteredLectures = lectures.filter((lecture) => {
        // console.log(lecture.lecture.course.id)
        return lecture.lecture.course.id == courseId
    })
    console.log(filteredLectures)

    const parseDate = (datetime) => {
        const date = datetime.substring(0, datetime.indexOf('T'))
        const day = date.substring(8, 10)
        const month = date.substring(5, 7)
        const year = date.substring(0, 4)

        return `${day}.${month}.${year}`
    }

    const parseTime = (datetime) => {
        const time = datetime.substring(datetime.indexOf('T') + 1, datetime.length - 3)
        return time
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
                    <ul className='lecture-list'>
                        {filteredLectures.map((lecture) => (
                            <li key={lecture.lecture.id} className='attendance-item'>
                                <div className='attendance-item-div'>
                                    <span>
                                        {parseDate(lecture.lecture.lectureStartDate)}, {parseTime(lecture.lecture.lectureStartDate)} - {parseTime(lecture.lecture.lectureEndDate)}
                                    </span>
                                    <button className='btn btn-primary'>View attended students</button>
                                </div>
                            </li>
                        ))}
                    </ul>
                </div>
            </main>
        </div>
    )
}

export default InstructorAttendanceDetailsPage