import React, { useState } from 'react'
import axios from 'axios'

import { Form, Button } from 'react-bootstrap'
import FadeIn from 'react-fade-in/lib/FadeIn'

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
        <div className="d-flex justify-content-center mt-5">
            <FadeIn>
                <Form className="w-100">
                    <h2>Registration</h2>
                    <Form.Group className="mt-4 mb-3" controlId="formBasicEmail">
                        <Form.Label>Username</Form.Label>
                        <Form.Control onChange={usernameHandler} type="text" placeholder="Enter username" />
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control onChange={passwordHandler} type="password" placeholder="Password" />
                    </Form.Group>

                    <Button onClick={submitHandler} variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
            </FadeIn>
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