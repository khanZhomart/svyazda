import React, { useContext, useState } from 'react'
import axios from 'axios'
import qs from 'qs'
import './css/login-page.css'
import { UserContext } from '../context/UserContext'



const LoginPage = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const {appToken, setAppToken, appUsername, setAppUsername} = useContext(UserContext)

    const usernameHandler = (event) => {
        setUsername(event.target.value)
    }
    const passwordHandler = (event) => {
        setPassword(event.target.value)
    }

    const submitHandler = (event) => {
        event.preventDefault()
        axios.post('http://localhost:8080/api/user/login',
            qs.stringify({
                username: username,
                password: password
            }), {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        }).then(response => {
            setAppToken(response.data.accessToken)
            setAppUsername(username)
        })

    }


    return (
        <div className="login-page">
            <h1>Login</h1>
            <form onSubmit={submitHandler}>
                <input placeholder='username' onChange={usernameHandler} />
                <input placeholder='password' onChange={passwordHandler} />
                <button>login</button>
            </form>
        </div>
    )
}

export default LoginPage