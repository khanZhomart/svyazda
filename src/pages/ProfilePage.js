import React, { useContext, useEffect, useState } from 'react'
import axios from 'axios'
import { UserContext } from '../context/UserContext'
import { NavLink, useParams } from 'react-router-dom'


const ProfilePage = () => {

    const [profile, setProfile] = useState('')
    const { appToken, appUsername } = useContext(UserContext)
    const [isFriend, setIsFriend] = useState(false)
    const { profileId } = useParams()

    useEffect(async () => {
        await axios.get(`http://localhost:8080/user-api/profile?id=${profileId}`,
            {
                headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${appToken}`
                }
            })
            .then(response => {
                setProfile(response.data)
                for(let i = 0; i < response.data.friends.length; i++) {
                    if(response.data.friends[i].username == appUsername) {
                        console.log('set to true')
                        setIsFriend(true)
                    }
                }
                
                console.log(response.data)
            }).catch(error => {
                setProfile('')
            } )
    }, [])

    const sendFriendRequest = async (id) => {
        await axios.post(`http://localhost:8080/friendship-api/friend-request?targetId=${id}`,
            {},
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${appToken}`
                }
            }).then(response => { console.log(response) })
    }

    return (
        <div className="profile-page">
            {profile == '' ? <div><h1>Denied. Either login or add to friends</h1></div> :
                <div>
                    <h1>Profile</h1>
                    <h3>Username:  {profile === null ? null : profile.username}</h3>
                    <h3>Posts: </h3>
                    {profile === null ? null : profile.posts.map((post, key) => {
                        return <div>
                            <div key={key}>
                                <h3>{post.title}</h3>
                                ---
                                <p>{post.text}</p>
                                ---
                                <p>author: {post.author.username}</p>
                                {post.disabledComments ? <p>comments disabled</p> : <NavLink to={`/post-comments/${post.postId}`} >comments section</NavLink> }</div>
                            <hr></hr>
                        </div>
                    })}
                    {profile === null ? null : isFriend ? <p>Your friend</p> : <button onClick={() => sendFriendRequest(profileId)}>Send friends request</button> }
                    <h3>Friends: </h3>
                    {profile === null ? null : profile.friends.map((friend, key) => {
                return <div key={key}>
                   <p>{friend.username}</p>
                </div>
            })}
                </div>
            }
        </div>
    )
}

export default ProfilePage