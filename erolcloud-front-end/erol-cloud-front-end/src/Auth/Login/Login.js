import React, {useState} from 'react'
import "./Login.css"
import Logo from "../../Assets/bilkent_logo.png"
import {useNavigate} from "react-router-dom";

const Login = () => {
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    let navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch("https://erolcloud-back-end.uc.r.appspot.com/api/v1/authentication/login", {
            method: "POST",
            headers: {
                "Content-type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(
                {email, password}
            )
        }).then((r) => {
            if (r.ok) {
                return r;
            }
            if (r.status === 401 || r.status === 403 || r.status === 500) {
                window.alert("Wrong email or password");
                return Promise.reject(new Error("hata oluştu"));
            }
            return Promise.reject(new Error("bilinmeyen hata"));
        }).then((r) => r.json()).then((response) => {
            localStorage.setItem("token", response.token);
            localStorage.setItem('email', response.email);
            localStorage.setItem('id', response.id);
            localStorage.setItem("role", response.roles[0]);
            localStorage.setItem("name", response.name);
            navigate('/redirecting');
        }).catch((e) => {
            console.log("here");
        });

    };

    return (
        <div className="login-body">
            <div className="card">
                <div className="intro">
                    <img src={Logo}
                         width="260"/>
                </div>
                <h1 className='title'>Attendance System</h1>
                <h5 className="mt-2">Welcome!</h5>
                <form className='d-flex flex-column justify-content-center align-items-center'
                      onSubmit={handleSubmit}>
                    <label>
                        <input type="text" className="form-control" placeholder="Email"
                               onChange={
                                   e => setEmail(e.target.value)
                               }/>
                    </label>
                    <label>
                        <input type="password" className="mt-3 form-control" placeholder="Password"
                               onChange={
                                   e => setPassword(e.target.value)
                               }/>
                    </label>
                    <div>
                        <button className="mt-3 btn btn-primary btn-block" type="submit">Login</button>
                    </div>
                </form>
                <p className="text-center form-check"
                   onClick={
                       () => navigate("/signup")
                   }>Don't have an account yet? Click here to register</p>
            </div>
        </div>
    )
}

export default Login