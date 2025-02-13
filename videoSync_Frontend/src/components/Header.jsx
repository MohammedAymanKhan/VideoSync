import { ArrowRightStartOnRectangleIcon } from "@heroicons/react/24/outline";
import { useNavigate } from "react-router-dom";

const Header = () => {
  const naviaget = useNavigate();

  const handleLogout = () => {
    console.log('logout');
    localStorage.removeItem('token');
    naviaget('/login');
  };

  return (
    <header className="h-18 fixed top-0 right-0 left-48 bg-card border-b border-muted/20 z-20 flex items-center justify-between px-6 bg-gray-900">
      <h1 className="text-2xl font-semibold text-white">VedoSync</h1>
      <button
        onClick={handleLogout}
        className="flex items-center space-x-2 px-4 py-2 text-gray-300 hover:bg-muted/50 rounded-lg transition-all duration-200"
      >
        <ArrowRightStartOnRectangleIcon className="w-5 h-5" />
        <span>Logout</span>
      </button>
    </header>
  );
};

export default Header;

