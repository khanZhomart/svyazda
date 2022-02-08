import React, { useState, useContext } from 'react'
import { NavLink, useHistory } from 'react-router-dom'
import { UserContext } from '../context/UserContext'


const Navbar = () => {
    const history = useHistory()
    const {appToken, setAppToken, appUsername, setAppUsername} = useContext(UserContext)
    const isLogged = appToken!=='' ? true : false
    
    const logout = () => {
        setAppToken('')
        setAppUsername('')
        history.push('/login')
    }

    return (
        <nav>
            {isLogged && <h3 style={{ 'marginLeft': '40px' }}>{appUsername}</h3>}
            <ul>
                {isLogged && <li><NavLink to='/my-profile'>My profile</NavLink></li>}
                {isLogged && <li><NavLink to='/do-post'>do a post</NavLink></li>}
                <li><NavLink to='/users'>Users</NavLink></li>
                <li><NavLink to='/posts'>Posts</NavLink></li>
                <li><NavLink to='/register'>Register</NavLink></li>
                {!isLogged && <li><NavLink to='/login'>Login</NavLink></li>}
                {isLogged && <li><button onClick={logout}>Logout</button></li>}
            </ul>
            <hr></hr>
        </nav>
    )
}

export default Navbar