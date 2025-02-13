import { Link } from "react-router-dom";
import {
  HomeIcon,
  FolderOpenIcon,
  ArrowUpTrayIcon,
  CalendarIcon,
  UserIcon,
} from "@heroicons/react/24/outline";

const Sidebar = () => {
  const menuItems = [
    { icon: HomeIcon, label: "Home", path: "/" },
    { icon: FolderOpenIcon, label: "Activity", path: "/activity" },
    { icon: CalendarIcon, label: "Upcoming", path: "/upcoming" },
    { icon: ArrowUpTrayIcon, label: "Upload", path: "/upload" },
  ];

  const dummyUser = {
    name: "Ayman",
    avatar: "/placeholder.svg",
  };

  return (
    <aside className="w-48 h-screen bg-gray-900 fixed left-0 top-0 z-30 border-r border-gray-700">
      <div className="flex flex-col h-full">
        
        <nav className="px-3 py-4 space-y-1 h-1/1 flex-col content-center">
          {menuItems.map((item) => (
            <Link
              key={item.label}
              to={item.path}
              className="flex items-center space-x-3 px-3 py-2 text-gray-300 hover:bg-gray-700 rounded-lg transition-all duration-200 text-base my-4">
              <item.icon className="w-5 h-5 text-base font-bold text-white" />
              <span className="font-medium">{item.label}</span>
            </Link>
          ))}
        </nav>

        <div className="mt-auto p-4 border-t border-gray-700">
          <div className="flex items-center gap-4px space-x-3 px-3">
            <div className="w-8 h-8 rounded-full bg-gray-700 flex items-center justify-center">
              <UserIcon className="w-5 h-5 text-gray-400" />
            </div>
            <span className="text-sm font-medium text-gray-300">
              {dummyUser.name}
            </span>
          </div>
        </div>

      </div>
    </aside>
  );

};

export default Sidebar;
