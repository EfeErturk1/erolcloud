import React, {useState, useEffect} from 'react'
import {useNavigate} from 'react-router-dom';
import './StudentCoursePage.css'

const StudentCoursePage = () => {
    let navigate = useNavigate()
    const [searchInput, setSearchInput] = useState('')
    const [courses, setCourses] = useState([])
    const studentId = localStorage.getItem('id')
    const [enrolledCourses, setEnrolledCourses] = useState([])

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/v1/courses', {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })
                const data = await response.json()
                setCourses(data);
            } catch (e) {
                console.log('Error fetching courses:', e)
            }
        }

        fetchCourses()
    }, [])

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

    const isEnrolled = (course) => {
        for (var i = 0; i < enrolledCourses.length; i++) {
            if (enrolledCourses[i].id === course.id)
                return true
        }
        return false
    }

    const handleEnroll = async (courseId, isEnrolled) => {
        if (!isEnrolled) {
            try {
                const response = await fetch('http://localhost:8080/api/v1/students/enrollments', {
                    method: 'PUT',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    },
                    body: JSON.stringify({
                        'studentId': studentId,
                        'courseId': courseId
                    })
                })
                const course = await response.json()
                if (response.ok)
                    window.alert(`Enrolled in course ${course.code}-${course.section}`)
                window.location.reload()
            } catch (e) {
                console.log('Error enrolling in course:', e)
            }
        } else {
            console.log(`Unenrolled from course ${courseId}`)
            // TODO unenrolling
        }
    }


     const filteredCourses = courses.filter((course) =>
         course.code.toLowerCase().includes(searchInput.toLowerCase()) ||
         course.name.toLowerCase().includes(searchInput.toLowerCase())
    )

    return(
        <div className='container'>
            <header className='header d-flex justify-content-end'>
                <button onClick={() => navigate('/')} className='btn btn-primary'>Home</button>
            </header>
            <main className='main d-flex flex-column align-items-center justify-content-center'>
                <h1>Courses</h1>
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
                            <div className='enroll-button-div'>
                                <button
                                    onClick={() => handleEnroll(course.id, isEnrolled(course))}
                                    className={`btn btn-primary ${isEnrolled(course) ? 'enroll-button-gray' : 'enroll-button'}`}
                                >
                                    {isEnrolled(course) ? 'Unenroll from course' : 'Enroll in course'}
                                </button>
                            </div>
                        </li>
                    ))}
                </ul>
            </main>
        </div>
    )
}

export default StudentCoursePage
