import React, {useState, useEffect} from 'react'
import {useNavigate} from 'react-router-dom';
import './StudentCoursePage.css'

// TODO: add enroll logic
// TODO: add unenroll logic (change button next to enrolled courses
const StudentCoursePage = () => {
    let navigate = useNavigate()
    const [searchInput, setSearchInput] = useState('')
    const [courses, setCourses] = useState([])

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/v1/courses', {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })
                const data = await response.json()
                setCourses(data);
            } catch (error) {
                console.log('Error fetching courses:', error)
            }
        };

        fetchCourses();
    }, []);

    const handleSearch = (event) => {
        setSearchInput(event.target.value)
    }

    const handleEnroll = (courseId) => {
        // Handle enrollment logic for the selected course
        console.log(`Enrolling in course ${courseId}`)
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
                                    onClick={() => handleEnroll(course.id)}
                                    className='btn btn-primary enroll-button'
                                >
                                    Enroll in course
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
