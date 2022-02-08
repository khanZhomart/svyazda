import React, { useState } from 'react'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import UsersPage from './pages/UsersPage'
import PostsPage from './pages/PostsPage'
import DoPostPage from './pages/DoPostPage'
import PostCommentsPage from './pages/PostCommentsPage'
import MyProfilePage from './pages/MyProfilePage'
import Navbar from './components/Navbar'
import { Route } from "react-router-dom"
import { UserContext } from './context/UserContext'


function App() {
  
  const [appToken, setAppToken] = useState('')
  const [appUsername, setAppUsername] = useState('')

  return (
    <div>
      <UserContext.Provider value={ { appToken, setAppToken, appUsername, setAppUsername } }>
      <Navbar/>
      <Route exact path='/login' render={() => <LoginPage />} />
      <Route exact path='/register' render={() => <RegisterPage />} />
      <Route exact path='/users' render={() => <UsersPage />} />
      <Route exact path='/posts' render={() => <PostsPage />} />
      <Route exact path='/do-post' render={() => <DoPostPage />} />
      <Route exact path='/my-profile' render={() => <MyProfilePage />} />
      <Route exact path='/post-comments/:postId' render={() => <PostCommentsPage />} />
      </UserContext.Provider>
    </div>
  )
} //
//<Route exact path='/profilePage/:id' render={() => <ProfilePage />} />
export default App;
