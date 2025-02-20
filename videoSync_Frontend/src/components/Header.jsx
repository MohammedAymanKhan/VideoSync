import { ArrowRightStartOnRectangleIcon, BellIcon } from "@heroicons/react/24/outline";
import { useNavigate } from "react-router-dom";
import { getContext } from "./AuthContext";

const Header = () => {
  const naviaget = useNavigate();
  const userContext = getContext();

  const handleLogout = () => {
    localStorage.removeItem('token');
    console.log('logout called')
    userContext.setUser(null);
    naviaget('/login');
  };

  return (
    <header className="col-start-2 col-end-3 row-start-1 row-end-2 px-5 z-20 flex items-center justify-between">

      <h1 className="text-2xl font-semibold text-white pl-4">VedoSync</h1>

      <div className="flex items-center justify-between pr-4">
        <button>
          <BellIcon className="h-6 w-6 text-gray-300 hover:text-slate-400" />
        </button>

        <button
          onClick={()=>{handleLogout()}}
          className="flex items-center gap-1 px-4 text-gray-300 hover:text-slate-400">
          <ArrowRightStartOnRectangleIcon className="w-5 h-5" />
          <span>Logout</span>
        </button>
      </div>

    </header>
  );
};

export default Header;

