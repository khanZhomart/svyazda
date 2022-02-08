import React, { useContext, useEffect, useState } from 'react'
import axios from 'axios'
import './css/login-page.css'
import { NavLink } from 'react-router-dom'



const UsersPage = () => {

    const [users, setUsers] = useState([])

    useEffect(() => {
        axios.get('http://localhost:8080/user-api/')
        .then(response => {
            setUsers(response.data)
        })
    }, [])


    return (
        <div className="users-page">
            <h1 style={{ 'marginLeft': '40px' }}>Users:</h1>
            {users.map((user, key) => {
                return <div key={key}>
                    <div style={{ 'marginLeft': '40px' }}>
                    <p>{user.username}</p>
                    <NavLink to={`/profile/${user.userId}`} >view profile</NavLink>
                    </div>
                    <hr></hr>
                </div>
            })}
        </div>
    )
}

export default UsersPage