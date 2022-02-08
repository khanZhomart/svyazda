import React, { useState, useContext } from 'react'
import axios from 'axios'
import { useHistory } from 'react-router-dom'
import { UserContext } from '../context/UserContext'
import './css/do-post-page.css'

const DoPostPage = () => {
    const history = useHistory()
    const [title, setTitle] = useState('')
    const [text, setText] = useState('')
    const [disabledComments, setDisabledComments] = useState(false)
    const [visibility, setVisibility] = useState('ALL')
    const { appToken } = useContext(UserContext)

    if(appToken === '') {
        return <h1>Login to post</h1>
    }

    const handleChange = () => {
        setDisabledComments(!disabledComments)
    }

    const titleHandler = (event) => {
        setTitle(event.target.value)
    }

    const textHandler = (event) => {
        setText(event.target.value)
    }

    const visibilityHandler = (event) => {
        setVisibility(event.target.value)
    }

    const submitHandler = (event) => {
        event.preventDefault()
        console.log(visibility)
        axios.post('http://localhost:8080/post-api/',
            { title: title, text: text, disabledComments: disabledComments, visibility: visibility }
            ,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${appToken}`
                }
            }
        )
        setText('')
        setTitle('')
        setVisibility('ALL')
        setDisabledComments(false)
    }

    return (
        <div classname='do-post-page'>
            <h1>Post</h1>
            <form onSubmit={submitHandler}>
                <input placeholder='title' value={title} onChange={titleHandler} /><br />
                <input placeholder='text' value={text} onChange={textHandler} /><br />
                <label>Disable comments</label>
                <input value={disabledComments} type='checkbox' placeholder='disable comments' checked={disabledComments} onChange={handleChange} /><br />
                <label>Visible to</label>
            <select onChange={visibilityHandler}>
                <option value='ALL'>all</option>
                <option value='AUTHORIZED'>authorized</option>
                <option value='FRIENDS'>friends</option>
            </select>
                <button oncClick={submitHandler}>post</button>
            </form>
            
        </div>
    )
}






export default DoPostPage