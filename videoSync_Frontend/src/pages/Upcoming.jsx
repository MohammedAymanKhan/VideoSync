import { useEffect, useState } from "react";
import useFetch from "../components/useFetch";
import IniviteVideo from "./UpcomingSection/IniviteVideo";
import { useLocation, useNavigate, useParams } from "react-router-dom";


export default function Upcoming(){
  const [pageLimit, setPageLimit] = useState({
    'page': 0, 'limit' : 8
  });
  const [videos, setVideos, getVideos] = useFetch(`/videos/usersUpcomingVideos?page=${pageLimit.page}&limit=${pageLimit.limit}`);

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(()=>{
    getVideos();
    setPageLimit({'page': pageLimit.page+1, 'limit':pageLimit.limit+8});
  },[]);

  const video = location.state?.video;

  const handleVideoSpecify = (video)=>{
    navigate(`/upcoming/${video.id}`, { state: { video } });
  }

  if (videos == null) {
    return <div>Loading...</div>;
  }
 
  return (
    <div className="px-8 py-4 h-full">
    
      <h2 className="text-2xl font-semibold mb-4">Upcoming Sessions</h2>

      {id && video ? (<IniviteVideo video={video}/>) 
        : 
        (<div className="flex flex-wrap gap-8 justify-start">

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
                <p className = 'text-sm text-slate-300 mb-2'>Starts: {video.startTime}</p>
                <div className="flex gap-2">
                  {video.sessionStarted ? <button onClick = {()=>{
                    navigate(`/session/${video.id}`)
                  }} className = 'text-sm bg-[#33C3F0] py-1 w-full rounded-sm text-black hover:bg-blue-500'>Join</button> :
                  // video.isHost? 
                  true?
                  <>
                    <button className = 'text-sm bg-[#33C3F0] py-1 w-full rounded-sm text-black hover:bg-blue-500'>Start</button>
                    <button className = 'text-sm bg-[#33C3F0] py-1 w-full rounded-sm text-black hover:bg-blue-500'
                    onClick={()=>{handleVideoSpecify(video)}}>invite</button>
                  </> : 
                  <button className = 'text-sm bg-[#33C3F0] py-1 w-full rounded-sm text-black hover:bg-blue-500'>view</button>
                  }
                </div>
              </div>
            </div>
          ))}

        </div>)
      }
    </div>);

}
