import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import './App.css'

import axios from "axios";
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import WorkoutForm from './components/WorkoutForm';
import ViewWorkout from './pages/ViewWorkouts';
import CreateGymGroup from './components/CreateGymGroup';
import GymGroupPage from './pages/GymGroupsPage';
import ViewGymGroups from './components/ViewGymGroups';
import JoinGymGroup from './components/JoinGymGroup';

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [newUser, setNewUser] = useState({ username: "", email: "", password: "", });
  const [user, setUser] = useState({ username: "", password: "", });
  const [registrationMessage, setRegistrationMessage] = useState('');

  const navigate = useNavigate()

  useEffect(() => {
    if (user.username !== "" || user.password !== "") {
      console.log("user changed", user);
      sendLogin();
    }
  }, [user]);

  useEffect(() => {
    if (newUser.username !== "" || newUser.email !== "" || newUser.password !== "") {
      registerUser();
    }
  }, [newUser]);

  useEffect(() => {
    if (loggedIn === false) {
      setUser({ email: "", password: "", });
    };
    
  }, [loggedIn])

  const registerUser = async (e) => {
    try {
      console.log("user: " + newUser.password);

      
      const res = await axios.post(`${import.meta.env.VITE_APP_GYMBACKEND}/api/auth/signup`, 
      {
        username: newUser.username,
        emailAddress: newUser.email,
        password: newUser.password,
        role: ["user"] || []
      });
      
      if (res.status === 200) {
        setRegistrationMessage('User successfully registered!');
        setTimeout(() => {
          navigate("/login")
        setRegistrationMessage('');
      }, 1000);
      }
    } catch (e) {
      console.log(e.response.data);
      setRegistrationMessage('Registration failed. Please try again.');
      setTimeout(() => {
        setRegistrationMessage('');
      }, 3000);
      
    }
  }

  const sendLogin = async (e) => {
      try {
        const res = await axios.post(`${import.meta.env.VITE_APP_GYMBACKEND}/api/auth/signin`, 
          {
            username: user.username,
            password: user.password
          }
        );

        console.log(res.data);
        
        
        if (res.status === 200) {
          const { accessToken, tokenType } = res.data;
          const token = `${tokenType} ${accessToken}`;
          localStorage.setItem('token', token);
          
          
          setLoggedIn(true);
          navigate("/log");
        };
      } catch (e) {
        console.log(e.response.data.message);
        setRegistrationMessage('Login failed. Please try again.');
        setTimeout(() => {
        setRegistrationMessage('');
      }, 3000);
      }
  }


  return (
    <div className='container d-flex flex-column' style={{ width: '100%', height: '100vh' }}>
      <Header loggedIn={loggedIn} setLoggedIn={setLoggedIn} />
      

      {loggedIn && (
        <Routes>
          <Route path="/log" element={<WorkoutForm user={user} />} />

          <Route path="/createGroup" element={<CreateGymGroup user={user} />} />

          <Route path="/joinGroup" element={<JoinGymGroup user={user} />} />

          <Route path="/gymGroups" element={<GymGroupPage user={user} />} />
          
          <Route path="/viewWorkouts" element={<ViewWorkout user={user} />} />
        </Routes>
      )}
      {!loggedIn && (

        <Routes>
          <Route path="/login" element={<LoginPage setUser={setUser} setLoggedIn={setLoggedIn} registrationMessage={registrationMessage} /> } />
          
          <Route path="/sign-up" element={<RegisterPage setNewUser={setNewUser} registrationMessage={registrationMessage}/>} />
          
        </Routes>
        
      )}
      <Footer />
    </div> 
  )
}

export default App
