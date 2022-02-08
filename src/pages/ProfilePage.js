import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'

const ProfilePage = () => {
    const [username, setUsername] = useState('')
    const [posts, setPosts] = useState([])
    const [friends, setFriends] = useState([])
    const [friendRequests, setFriendRequests] = useState([])
    const { id } = useParams()

    useEffect(() => {
        axios.get(`http://localhost:8080/user-api/profile?id=${id}`,
        {
            headers: 
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then(response => {
                console.log(response.data)
                setTitle(response.data.title)
                setDescription(response.data.description)
                setPrice(response.data.price)
                setUser(response.data.user)
            })
    }, [id])

    return (
        <div>
            <h3>{id}</h3>
            <h3>{title}</h3>
            <h3>{description}</h3>
            <h3>{price}</h3>
            <h2>Contact details of classified owner</h2>
            <h3>{user.firstName}</h3>
            <h3>{user.secondName}</h3>
            <h3>{user.email}</h3>
            <h3>{user.phoneNumber}</h3>
        </div>
    )
}

export default ProfilePage