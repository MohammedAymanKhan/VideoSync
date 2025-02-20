import { Outlet } from 'react-router-dom';
import Sidebar from "../components/Sidebar"
import Header from "../components/Header"

const DashBoard = () => {
  return (
    <div className=" bg-gray-900 w-screen h-screen grid grid-cols-[190px_1fr] grid-rows-[70px_1fr]">
  
      <Sidebar/>
  
      <Header/>
        
      <main className="col-start-2 col-end-3 row-start-2 row-end-3 bg-slate-950 text-white">
        <Outlet />
      </main>
      
    </div>
  );  
}

export default DashBoard;