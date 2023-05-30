import React, {useState, useEffect} from 'react'
import {useNavigate} from 'react-router-dom';

const InstructorAttendancesPage = () => {
    let navigate = useNavigate()
    const [courses, setCourses] = useState([])
    const [searchInput, setSearchInput] = useState('')
    const instructorId = localStorage.getItem('id')

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/instructors/${instructorId}/courses`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })
                const data = await response.json()
                setCourses(data)
            } catch (e) {
                console.log('Error fetching courses:', e)
            }
        }

        fetchCourses()
    }, [])

    const handleSearch = (event) => {
        setSearchInput(event.target.value)
    }

    const filteredCourses = courses.filter((course) =>
        course.code.toLowerCase().includes(searchInput.toLowerCase()) ||
        course.name.toLowerCase().includes(searchInput.toLowerCase())
    )

    const handleViewAttendance = (courseId) => {
        // TODO go to course attendance page
        console.log('View attendance data for ' + courseId)
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
                                <span className='course-code'>{course.code}</span>
                                <span className='course-name'>{course.name}</span>
                                <span className='course-section'>Section {course.section}</span>
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

export default InstructorAttendancesPage
