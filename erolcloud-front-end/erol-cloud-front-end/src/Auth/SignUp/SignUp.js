import React, {useState} from 'react'
import "./SignUp.css"
import Logo from "../../Assets/bilkent_logo.png"
import {useNavigate} from "react-router-dom";


// T
const SignUp = () => {
    let navigate = useNavigate()
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");

    const handleSubmit = (event) => {
        if (email === "" || password === "" || name === "") {
            window.alert("No fields can be left empty!")
        } else if (!email.includes("@ug.bilkent.edu.tr")) {
            window.alert("Only Bilkent students can register")
        } else if (password !== passwordRepeat) {
            window.alert("Provided passwords are incorrect")

        } else {
            event.preventDefault();

            fetch("http://localhost:8080/api/v1/authentication/register", {
                method: "POST",
                headers: {
                    "Content-type": "application/json",
                    "Accept": "application/json"
                },
                body: JSON.stringify(
                    {name: name, email, password}
                )
            }).then((r) => {
                if (r.ok) {
                    navigate("/")
                } else if (r.status === 401 || r.status === 403 || r.status === 500) {
                    return Promise.reject(new Error("hata oluştu"));
                } else {
                    return Promise.reject(new Error("bilinmeyen hata"));
                }
            })
        }
    };

    return (

        <div className="signup_body">
            <div className="card text-center">
                <div className="intro">
                    <img src={Logo}
                         width="160"/>
                </div>
                <div className="mt-4 text-center">
                    <h4>Don't have an account?</h4>
                    <h6>Register now</h6>


                    <form className='d-flex flex-column justify-content-center align-items-center'
                          onSubmit={handleSubmit}>
                        <label>
                            <input type="text" className=" mt-2 form-control" placeholder="Name"
                                   onChange={
                                       e => setName(e.target.value)
                                   }/>
                        </label>
                        <label>
                            <input type="text" className=" mt-2 form-control" placeholder="Email"
                                   onChange={
                                       e => setEmail(e.target.value)
                                   }/>
                        </label>
                        <label>
                            <input type="password" className="mt-2 form-control" placeholder="Password"
                                   onChange={
                                       e => setPassword(e.target.value)
                                   }/>
                        </label>
                        <label>
                            <input type="password" className="mt-3 form-control" placeholder="Password Again"
                                   onChange={
                                       e => setPasswordRepeat(e.target.value)
                                   }/>
                        </label>
                        <div>
                            <button className="mt-3 btn btn-primary btn-block" type="submit">Register</button>
                        </div>
                    </form>


                    <div className="text-center intro">
                        <p className="form-check"
                           onClick={
                               () => {
                                   navigate("/")
                               }
                           }>
                            Already have an account? Click here to login
                        </p>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SignUp