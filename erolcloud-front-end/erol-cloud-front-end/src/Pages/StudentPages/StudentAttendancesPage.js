import React, {useState, useEffect} from 'react'
import {Link, useNavigate} from 'react-router-dom';

const StudentAttendancesPage = () => {
    let navigate = useNavigate()
    const [enrolledCourses, setEnrolledCourses] = useState([])
    const [searchInput, setSearchInput] = useState('')
    const studentId = localStorage.getItem('id')

    useEffect(() => {
        const fetchEnrolledCourses = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/students/${studentId}/enrollments`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })
                const data = await response.json()
                setEnrolledCourses(data)
            } catch (e) {
                console.log('Error fetching enrolled courses:', e)
            }
        }

        fetchEnrolledCourses()
    }, [])

    const handleSearch = (event) => {
        setSearchInput(event.target.value)
    }

    const filteredCourses = enrolledCourses.filter((course) =>
        course.code.toLowerCase().includes(searchInput.toLowerCase()) ||
        course.name.toLowerCase().includes(searchInput.toLowerCase())
    )

    const handleViewAttendance = (courseId) => {
        navigate(`/attendance-details/${courseId}`)
    }

    return (
        <div className='container'>
            <div className="row mt-4">
                <div className="col-12">
                    <button className="btn btn-primary" onClick={() => navigate('/')}>Home</button>
                </div>
            </div>
            <main className='main d-flex flex-column align-items-center justify-content-center'>
                <h1>Attendances</h1>
                <div className='search-bar'>
                    <input
                        type='text'
                        value={searchInput}
                        onChange={handleSearch}
                        placeholder='Search courses'
                    />
                </div>
                <ul className='course-list'>
                    {filteredCourses.map((course) => (
                        <li key={course.id}>
                            <div className='course-details'>
                                <span className='course-code'>{course.code}-{course.section}</span>
                                <span className='course-name'>{course.name}</span>
                            </div>
                            <div className='view-button-div'>
                                <button
                                    onClick={() => handleViewAttendance(course.id)}
                                    className='btn btn-primary'
                                >
                                    View attendance
                                </button>
                            </div>
                        </li>
                    ))}
                </ul>
            </main>
        </div>
    )
}

export default StudentAttendancesPage
