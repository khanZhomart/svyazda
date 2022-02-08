import React, { useContext, useEffect, useState } from 'react'
import axios from 'axios'
import { UserContext } from '../context/UserContext'
import { NavLink } from 'react-router-dom'


const MyProfilePage = () => {

    const [profile, setProfile] = useState({})
    const {appToken} = useContext(UserContext)

    useEffect(() => {
        axios.get('http://localhost:8080/user-api/profile?id=0',
        {
            headers: 
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${appToken}`
            }
        })
        .then(response => {
            setProfile(response.data)
            console.log(response.data)
        })
    }, [])

    const enableComments = () => {
        axios.put('http://localhost:8080/post-api/enable-comment?postId=1', {},
        {
            headers: 
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${appToken}`
            }
        }).then(response => console.log(response.data))
    }

    const disableComments = async () => {
        console.log('sdf')
        await axios.put('http://localhost:8080/post-api/disable-comment?postId=1', {},
        {
            headers: 
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${appToken}`
            }
        }).then(response => {
            
        })
    }

    return (
        <div className="my-profile-page">
            <h3>Username: </h3>{profile.username}
            <h3>Posts: </h3>
            {profile.posts === undefined ? null : profile.posts.map((post, key) => {
                return <div>
                    <div key={key}>
                    <h3>{post.title}</h3>
                    ---
                    <p>{post.text}</p>
                    ---
                    <p>author: {post.author.username}</p>
                    <p>posted on: {post.createdAt}</p>
                    {post.disabledComments ? <div><p>comments disabled</p> <button onClick={enableComments}>enable</button></div> : <div><NavLink to={`/post-comments/${post.postId}`} >comments section</NavLink><button onClick={disableComments}>disable</button></div> }
                </div>
                <hr></hr>
                </div>
            }).reverse()}
        </div>
    )
}

export default MyProfilePage