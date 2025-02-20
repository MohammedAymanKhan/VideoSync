import { useEffect, useState } from "react";
import useFetch from "../components/useFetch";

export default function Activity(){

  const [pageLimit, setPageLimit] = useState({
    'page': 0, 'limit' : 8
  });
  const [videos, setVideos, getVideos] = useFetch( `/videos/usersFinishedVideos?page=${pageLimit.page}&limit=${pageLimit.limit}`);
  
    useEffect(()=>{
      getVideos();
      setPageLimit({'page': pageLimit.page+1, 'limit':pageLimit.limit+8});
    },[]);
  
    if (videos == null) {
      return <div>Loading...</div>;
    }

    return (
      <div className="m-10">
      
        <h2 className="text-2xl font-semibold mb-6">Activity Sessions</h2>
  
        <div className="flex flex-wrap gap-8 justify-start">
  
          {videos.map((video)=>(
            <div className="basis-55 h-fit flex-none bg-gray-900 rounded-md shadow-xl" key = {video.id}>
              <div className="aspect-video">
                <img 
                  src={`data:image/jpeg;base64,${video.thumbnailData}`} 
                  alt={video.title}
                  className="aspect-video rounded-t-md "/>
              </div>
  
              <div className="p-3.5">
                <h3 className="font-medium text-base mb-2">{video.title}</h3>
                <p className = 'text-sm text-slate-300 mb-2'>Created On: {video.createdAt}</p>
                <button className = 'text-sm bg-[#33C3F0] w-full py-1 rounded-sm text-black'>View History</button>
              </div>
            </div>
          ))}
  
        </div>
  
      </div>);
}