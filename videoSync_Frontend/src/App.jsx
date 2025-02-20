import { Routes, Route } from "react-router-dom"

import Login from "./pages/Login"
import OAuthTokenRedirect from "./components/OAuthTokenRedirect"
import PrivateRoute from "./components/PrivateRoute"
import DashBoard from "./pages/DashBoard"
import Home from './pages/Home';
import Activity from './pages/Activity';
import Upcoming from './pages/Upcoming';
import Upload from './pages/Upload';
import NotFound from './pages/NotFound';
import Session from "./pages/Session"
import { useState } from "react"
import { AuthContext } from "./components/AuthContext"

function App() {
  const [authUser, setAuthUser] = useState(null);
  return (
    <AuthContext.Provider value={{authUser,setAuthUser}}>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path = '/login/oauth2/token/google?' element = {<OAuthTokenRedirect/>}/>
        <Route element = {<PrivateRoute/>}>
            <Route path="/session/:videoId" element={<Session />} />
            <Route element={<DashBoard/>}>
              <Route path="/" element={<Home/>} />
              <Route path="/activity" element={<Activity/>} />
              <Route path="/upcoming/:id?" element={<Upcoming/>} />
              <Route path="/upload" element={<Upload/>} />
              <Route path="/notfound" element={<NotFound/>} />
            </Route>  
        </Route>
      </Routes>
    </AuthContext.Provider>
  );
}

export default App;


