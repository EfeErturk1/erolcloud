import React, {useState, useEffect} from 'react'
import {useParams, useNavigate} from 'react-router-dom';

const InstructorAttendedStudentsPage = () => {
    let navigate = useNavigate()
    const {courseId, lectureId} = useParams()
    const [searchInput, setSearchInput] = useState('')
    const [students, setStudents] = useState([])
    const instructorId = localStorage.getItem('id')

    useEffect(() => {
        const fetchStudents = async () => {
            try {
                const response = fetch(`https://erolcloud-back-end.uc.r.appspot.com/instructors/${instructorId}/attendances`, {
                    method: 'GET',
                    headers: {
                        'Content-type': 'application/json',
                        Authorization: `Bearer ${localStorage.getItem('token')}`
                    }
                })
                const data = await response.json()
                if (response.ok) {
                    console.log(data.studentAttendances)
                    setStudents(data)
                    let attendedStudents = data
                }
            } catch (e) {
                console.log('Error fetching students:', e)
            }
        }
    })

    const filteredStudents = students.filter((student) =>
        student.name.toLowerCase().includes(searchInput.toLowerCase()) ||
        student.email.toLowerCase().includes(searchInput.toLowerCase())
    )

    const handleSearch = (event) => {
        setSearchInput(event.target.value)
    }

    return(
        <div className='container'>
            <div className="row mt-4">
                <div className="col-12">
                    <button className="btn btn-primary" onClick={() => navigate('/')}>Home</button>
                </div>
            </div>
            <main className='main d-flex flex-column align-items-center justify-content-center'>
                <h1>Attended Students</h1>
                <div className='search-bar'>
                    <input
                        type='text'
                        value={searchInput}
                        onChange={handleSearch}
                        placeholder='Search students'
                    />
                </div>
                <ul className='course-list'>
                    {filteredStudents.map((student) => (
                        <li key={student.id}>
                            <div className='course-details'>
                                <span className='course-code'>{student.name}</span>
                                <span className='course-name'>{student.email}</span>
                            </div>
                        </li>
                    ))}
                </ul>
            </main>
        </div>
    )
}

export default InstructorAttendedStudentsPage