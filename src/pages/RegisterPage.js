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

    const submitHandler = async (event) => {
        event.preventDefault()
        await axios.post('http://localhost:8080/user-api/',
        { username: username, password: password }
        ).then(response => {
            console.log(response)
            
        })

    }


    return (
        <div className="register-page">
            <h1>Register</h1>
            <input placeholder='username' value={username} onChange={usernameHandler} />
            <input placeholder='password' value={password} onChange={passwordHandler} />
            <button onClick={submitHandler}>register</button>
           
        </div>
    )
}
/*
 <form onSubmit={submitHandler}>
                <input placeholder='username' value={username} onChange={usernameHandler} />
                <input placeholder='password' value={password} onChange={passwordHandler} />
                <button>register</button>
            </form>
*/
export default RegisterPage