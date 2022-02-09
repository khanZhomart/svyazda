import React, { useContext, useEffect, useState } from 'react'
import axios from 'axios'
import { UserContext } from '../context/UserContext'
import { NavLink } from 'react-router-dom'

import { Card, ListGroup } from 'react-bootstrap'

const MyProfilePage = () => {
    const [profile, setProfile] = useState({})
    const {appToken} = useContext(UserContext)
    const [success, setSuccess] = useState(true)

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
    }, [success])

    const enableComments = async () => {
        await axios.put('http://localhost:8080/post-api/enable-comment?postId=1', {},
        {
            headers: 
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${appToken}`
            }
        }).then(response => {
            console.log(response)
            setSuccess(!success)
            
        })
    }

    const disableComments = async () => {
        await axios.put('http://localhost:8080/post-api/disable-comment?postId=1', {},
        {
            headers: 
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${appToken}`
            }
        }).then(response => {
            console.log(response)
            setSuccess(!success)
        })
    }

    return (
        <div className="d-flex justify-content-center mt-5">
            <div>
                <p>{profile.username}</p>
                <div>
                    <Card style={{ width: '18rem' }}>
                        <Card.Header><b>Posts</b></Card.Header>
                        <ListGroup variant="flush">
                            {profile.posts === undefined ? null : profile.posts.map((post, key) => {
                                <ListGroup.Item key={key}>
                                    
                                </ListGroup.Item>
                            })}
                        </ListGroup>
                    </Card>
                </div>
            </div>
        </div>
        // <div className="my-profile-page">
        //     <h3>Username: </h3>{profile.username}
        //     <h3>Posts: </h3>
        //     {profile.posts === undefined ? null : profile.posts.map((post, key) => {
        //         return <div>
        //             <div key={key}>
        //             <h3>{post.title}</h3>
        //             ---
        //             <p>{post.text}</p>
        //             ---
        //             <p>author: {post.author.username}</p>
        //             {post.disabledComments ? <div><p>comments disabled</p> <button onClick={enableComments}>enable</button></div> : <div><NavLink to={`/post-comments/${post.postId}`} >comments section</NavLink><button onClick={disableComments}>disable</button></div> }
        //         </div>
        //         <hr></hr>
        //         </div>
        //     }).reverse()}
        // </div>
    )
}

export default MyProfilePage