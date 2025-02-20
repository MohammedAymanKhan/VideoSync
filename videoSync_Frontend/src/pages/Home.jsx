import { useEffect, useState } from "react";
import useFetch from "../components/useFetch";
import usePost from "../components/usePost";
import { getContext } from "../components/AuthContext";
import responseDisplay from "../components/responseDisplay";


export default function Home(){
  const [pageLimit, setPageLimit] = useState({
    'page': 0, 'limit' : 8
  });
  const [videos, setVideos, getVideos] = useFetch(`/videos/publicVideos?page=${pageLimit.page}&limit=${pageLimit.limit}`);
  const [requestStatus, setRequestStatus, postRequestJoin] = usePost(`/participate/requestJoin`);
  const AuthCont = getContext();

  useEffect(()=>{
    getVideos();
    setPageLimit({'page': pageLimit.page+1, 'limit':pageLimit.limit+8});
  },[]);

  const handleRequestJoin = (videoId) => {
    console.log(AuthCont.user);
    postRequestJoin({
      'userId' : AuthCont.user.id, 
      'videoId' : videoId
    });
  };


  if (videos == null) {
    return <div>Loading...</div>;
  }
  
  return (
    <div className="m-10 absolute">
    
      {requestStatus != null && responseDisplay(requestStatus.status,['request was sent successfully','attempt failed, retry!'],setRequestStatus)}

      <h2 className="text-2xl font-semibold mb-6">Videos</h2>

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
              <p className = 'text-sm text-slate-300 mb-2'>Start Time: {video.startTime}</p>
              <button onClick={() => {handleRequestJoin(video.id)}} className = 'text-sm bg-[#33C3F0] w-full py-1 rounded-sm text-black'>Request</button>
            </div>
          </div>
        ))}

      </div>

    </div>);
}