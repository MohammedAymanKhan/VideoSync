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




function App() {

  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path = '/login/oauth2/token/google?' element = {<OAuthTokenRedirect/>}/>
      <Route element = {<PrivateRoute/>}>
        <Route element={<DashBoard/>}>
            <Route path="/" element={<Home/>} />
            <Route path="/activity" element={<Activity/>} />
            <Route path="/upcoming" element={<Upcoming/>} />
            <Route path="/upload" element={<Upload/>} />
            <Route path="*" element={<NotFound/>} />
        </Route>
      </Route>
    </Routes>
  );
}

export default App;


