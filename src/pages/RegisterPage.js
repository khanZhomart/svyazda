import React, { useState } from 'react'
import axios from 'axios'
import './css/register-page.css'


const RegisterPage = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const usernameHandler = (event) => {
        setUsername(event.target.value)
    }
    const passwordHandler = (event) => {
        setPassword(event.target.value)
    }

    const submitHandler = (event) => {
        event.preventDefault()
        axios.post('http://localhost:8080/user-api/',
        { username, password }
        ,
        {
            headers: {
                'Content-Type': 'application/json'
            }
        }
        ).then(response => {
            console.log(response)
            
        })

    }


    return (
        <div className="register-page">
            <h1>Register</h1>
            <form onSubmit={submitHandler}>
                <input placeholder='username' onChange={usernameHandler} />
                <input placeholder='password' onChange={passwordHandler} />
                <button>register</button>
            </form>
        </div>
    )
}

export default RegisterPage