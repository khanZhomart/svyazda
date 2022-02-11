import React, { useContext, useEffect, useState } from 'react'
import axios from 'axios'
import { UserContext } from '../context/UserContext'
import { NavLink } from 'react-router-dom'


const MyProfilePage = () => {

    const [profile, setProfile] = useState({})
    const [visibility, setVisibility] = useState('ALL')
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

    const enableComments = async (id) => {
        console.log(id)
        await axios.put(`http://localhost:8080/post-api/enable-comment?postId=${id}`, {},
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

    const disableComments = async (id) => {
        console.log(id)
        await axios.put(`http://localhost:8080/post-api/disable-comment?postId=${id}`, {},
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

    const visibilityHandler = (event) => {
        setVisibility(event.target.value)
    }

    const sendVisibility = async () => {
        await axios.put('http://localhost:8080/user-api/', 
        {profilePageVisibility: visibility}, 
        {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${appToken}`
            }
        }).then(response => {console.log(response)})
    }

    const acceptFriendRequest = async (id) => {
        await axios.post(`http://localhost:8080/friendship-api/friend-accept?sourceId=${id}`,
            {},
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${appToken}`
                }
            }).then(response => { console.log(response) })
    }

    return (
        <div className="my-profile-page">
            <h1>My Profile</h1>
            <select onChange={visibilityHandler}>
                <option value='ALL'>all</option>
                <option value='AUTHORIZED'>authorized</option>
                <option value='FRIENDS'>friends</option>
            </select>
            <button onClick={sendVisibility} >Set visibility</button>
            <h3>Username: </h3>{profile.username}
            <hr></hr>
            <h3>Posts: </h3>
            {profile.posts === undefined ? 
                null 
            : 
                profile.posts.map((post, key) => {
                return <div>
                    <div key={key}>
                    <h3>{post.title}</h3>
                    ---------
                    <p>{post.text}</p>
                    ---------
                    <p>author: {post.author.username}</p>
                    {post.disabledComments ? <div><p>comments disabled</p> <button onClick={() => enableComments(post.postId)}>enable</button></div> : <div><NavLink to={`/post-comments/${post.postId}`} >comments section</NavLink><button onClick={() => disableComments(post.postId)}>disable</button></div> }
                </div>
                <hr></hr>
                </div>
            })}
            <h3>Friends: </h3>
            {profile.friends === undefined ? null : profile.friends.map((friend, key) => {
                return <div key={key}>
                   <p>{friend.username}</p>
                </div>
            })}
            <hr></hr>
            <h3>Friend requests: </h3>
            {profile.friendRequests === undefined ? null : profile.friendRequests.map((friendRequest, key) => {
                return <div key={key}>
                   <p>{friendRequest.username}</p>
                   <button onClick={() => acceptFriendRequest(friendRequest.userId)}>Accept friend request</button>
                </div>
            })}
        </div>
    )
}

export default MyProfilePage