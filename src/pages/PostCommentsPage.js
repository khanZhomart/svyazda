import React, { useEffect, useState, useContext } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'
import { UserContext } from '../context/UserContext'

const PostCommentsPage = (props) => {
    const [post, setPost] = useState('')
    const [comments, setComments] = useState([])
    const [yourComment, setYourComment] = useState('')
    const { postId } = useParams()
    const { appToken } = useContext(UserContext)

    useEffect(() => {
        axios.get(`http://localhost:8080/comment-api/post-comments?postId=${postId}`)
            .then(response => {
                setComments(response.data)
            })

        axios.get(`http://localhost:8080/post-api/post?id=${postId}`,
            {
                headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${appToken}`
                }
            })
            .then(response => {
                setPost(response.data)
            })
    }, [])

    const commentHandler = (event) => {
        setYourComment(event.target.value)
    }

    const submitComment = async (event) => {
        event.preventDefault()
        await axios.post(`http://localhost:8080/comment-api/`,
            { 
                postId: postId, 
                text: yourComment 
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${appToken}`
                }
            })
        await axios.get(`http://localhost:8080/comment-api/post-comments?postId=${postId}`)
            .then(response => {
                setComments(response.data)
            })
        setYourComment('')
    }

    return (
        <div style={{ 'marginLeft': '40px' }}>

            {post === '' ?
                <h2>Comments are disabled</h2>
                :
                <div>
                    <div style={{ border: '5px solid rgba(0, 0, 0, 0.5)', padding: '5px' }}> 
                        <h3>{post.title}</h3>
                        -------
                        <p>{post.text}</p>
                        -------
                        {post.author !== undefined ? 
                            <p>
                                author: {post.author.username}
                            </p> 
                        : 
                            null
                        }
                        <p>
                            posted on: {post.createdAt}
                        </p>
                    </div>

                    <h4>Comments:</h4>
                        {appToken === '' ? <p>Login to comment</p> :
                            <div>
                                <input placeholder='Your comment' value={yourComment} onChange={commentHandler} />
                                <button onClick={submitComment}>Leave comment</button>
                            </div>
                        }
                        {comments.map((comment, key) => {
                            return <div>
                                <div key={key}>
                                    <h3>{comment.author.username}</h3>
                                    <p>{comment.text}</p>
                                    <p>date: {comment.createdAt}</p>
                                </div>
                                <hr></hr>
                            </div>
                        }).reverse()}
                </div>
            }





        </div>
    )
}

export default PostCommentsPage