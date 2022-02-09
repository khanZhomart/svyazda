import React, { useContext, useState } from 'react'
import axios from 'axios'
import qs from 'qs'
import './css/login-page.css'
import { UserContext } from '../context/UserContext'

import { Form, Button, Fade } from 'react-bootstrap'
import FadeIn from 'react-fade-in/lib/FadeIn'

const LoginPage = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const {setAppToken, setAppUsername} = useContext(UserContext)

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
        <div className="d-flex justify-content-center mt-5">
            <FadeIn>
                <Form className="w-100">
                    <h2>Login</h2>
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

export default LoginPage