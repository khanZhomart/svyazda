import React, { useState } from 'react'
import axios from 'axios'
import { Redirect, useHistory } from 'react-router-dom'

const DoPostPage = () => {
    const history = useHistory()

        axios.post('http://localhost:8080/post_classified',
            { title, description, price }
            ,
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            }
        ).then(response => {
            console.log(response.data.access_token)
        }).catch(error => {
            console.log(error.response)
            console.log(localStorage.getItem('refresh_token'))

            if (error.response.data.access_token.includes('The Token has expired on')) {
                axios.get('http://localhost:8080/token/refresh'
                    ,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${localStorage.getItem('refresh_token')}`
                        }
                    }
                ).then(response => {
                    console.log(response.data)
                    localStorage.setItem('token', response.data.access_token)
                    localStorage.setItem('refresh_token', response.data.refresh_token)
                    axios.post('http://localhost:8080/post_classified',
                        { title, description, price }
                        ,
                        {
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': `Bearer ${response.data.access_token}`
                            }
                        }
                    ).then(response => {
                        console.log(response.data)
                    })
                }).catch(error => {
                    history.push('/login')
                })
            }

        })
    }

    function handleChange() {
		setDisabledComments(!disabledComments)
	}

    return (
        <div>
            <h1>Post</h1>
            <form onSubmit={submitHandler}>
                <input placeholder='title' onChange={titleHandler} />
                <input placeholder='text' onChange={textHandler} />
                <input type='checkbox' placeholder='disable comments' checked={disabledComments} onChange={handleChange} />
                <button >post</button>
            </form>
            <label>Category</label>
            <select onChange={visibilityHandler}>
                <option value='None'>None</option>
                <option value='Tech'>Tech</option>
                <option value='Properties'>Properties</option>
            </select>
        </div>
    )
}

export default DoPostPage