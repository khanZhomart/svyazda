import React, { useContext, useEffect, useState } from 'react'
import axios from 'axios'
import './css/login-page.css'
import { UserContext } from '../context/UserContext'
import { NavLink } from 'react-router-dom'


const PostsPage = () => {

    const [posts, setPosts] = useState([])
    const {appToken} = useContext(UserContext)

    useEffect(() => {
        axios.get('http://localhost:8080/post-api/',
        {
            headers: 
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${appToken}`
            }
        })
        .then(response => {
            setPosts(response.data)
        })
    }, [])

    return (
        <div className="posts-page">
            <h1 style={{ 'marginLeft': '40px' }}>Posts:</h1>
            {posts.map((post, key) => {
                return <div>
                    <div style={{ 'marginLeft': '40px' }} key={key}>
                    <h3>{post.title}</h3>
                    ---
                    <p>{post.text}</p>
                    ---
                    <p>author: {post.author.username}</p>
                    <p>posted on: {post.createdAt}</p>
                    {post.disabledComments ? <p>comments disabled</p> : <NavLink to={`/post-comments/${post.postId}`} >comments section</NavLink> }
                </div>
                <hr></hr>
                </div>
            }).reverse()}
        </div>
    )
}

export default PostsPage