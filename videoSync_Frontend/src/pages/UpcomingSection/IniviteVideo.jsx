import { MagnifyingGlassIcon, 
          PaperAirplaneIcon, 
          UserIcon, ClockIcon } from "@heroicons/react/24/outline";
import useFetch from "../../components/useFetch";
import { useEffect, useState } from "react";
import usePost from "../../components/usePost";
import responseDisplay from "../../components/responseDisplay";

function IniviteVideo({video}){
  
  const [query, setQuery] = useState('');
  const [users, setUsers] = useState([]);
  const [inviteResponse, setInviteResponse, sendInviteResponse] =
  usePost('/participate/inviteJoin');
 
  const[videoParicipants, setVideoParicipants, getVideoParicipants] = 
    useFetch(`/participate/videoParticipants/${video.id}`);

    useEffect(()=>{
      getVideoParicipants();
    },[]);
    
    useEffect(() => {
      const delayDebounceFn = setTimeout(() => {
        if (query.trim() !== '') {
          console.log('user fetch called');
          fetch(`http://localhost:8080/api/user/usersSearch?query=${encodeURIComponent(query)}`,{
            method : 'GET',
            headers : { 
              Authorization: `Bearer ${localStorage.getItem('token')}`,
              "Accept": "application/json",
            } 
          })
            .then((res) => res.json())
            .then((data) => setUsers(data))
            .catch((err) => console.error('Error fetching users:', err));
        } else {
          setUsers([]);
        }
      }, 700);
  
      return () => clearTimeout(delayDebounceFn);
    }, [query]);

    const handleInvite = (userId) =>{
      sendInviteResponse({'userId' : userId, 'videoId': video.id});
    }

  return(
    <div className="flex flex-nowrap gap-6 shadow-lg relative" key={video.id}>
      
      {inviteResponse != null && 
      responseDisplay(inviteResponse?.status,['successfully send invite request','failed. Please try again'], setInviteResponse)}

      <main className="flex flex-nowrap gap-4 basis-7/10 rounded-xl bg-gray-800">

        <section className="flex flex-col gap-2 basis-7/10 bg-gray-950 rounded-xl">
          <div className="aspect-3/2 rounded-t-xl">
            <img src={`data:image/jpeg;base64,${video.thumbnailData}`}  
            alt={video.title} className="aspect-3/2 rounded-t-xl" />
          </div>

          <h3 className="mt-2 text-xl font-bold">
            {video.title}
          </h3>

          <div className="flex flex-nowrap w-full justify-strat items-center gap-4">
            <div className="flex flex-nowrap gap-2 text-sm items-center" key={video.hostUserId}>
              <UserIcon className=" w-7 h-7 text-black rounded-full bg-blue-500 p-1" />
              {video.hostUserName}
            </div>

            <button className="flex flex-nowrap gap-1 bg-blue-500 text-black px-1 py-1 rounded-sm items-center hover:bg-blue-400">
              <PaperAirplaneIcon className="h-5 w-5 text-black" />
              Start
            </button>
          </div>

          <div className="flex flex-nowrap text-slate-400 gap-2 my-2">
            <ClockIcon className="h-6 w-6 text-gray-500" />
            <p>Starts: {video.startTime}</p>
          </div>

        </section>

        <section className="basis-3/10 bg-gray-950 w-full rounded-xl p-4">
          <h3 className="text-2xl border-b-2 border-slate-700 mb-2">Participants</h3>
          <ul className="flex flex-col gap-3 items-start justify-start overflow-auto h-full text-xs">
            {videoParicipants == null ? <li className="bg-gray-900 rounded-xl w-full p-2">loading...</li> :
              videoParicipants?.map((videoParicipant)=>(
              <li className="bg-gray-900 rounded-xl w-full p-2" key={videoParicipant.id}>  
                <div key={videoParicipant.userId}>
                  <p>{videoParicipant.name}</p>
                  <p>{videoParicipant.email}</p>
                </div>
              </li>
              ))
            }
          </ul>
        </section>

      </main>

      <aside className="basis-3/10 flex flex-col justify-between rounded-md p-4 bg-gray-950">

        <ul className="flex flex-col gap-3 items-start justify-start overflow-auto text-xs">
        {users?.map((user) => (
          <li className="group bg-gray-900 rounded-xl w-full p-2 flex justify-between items-center hover:bg-gray-800 transition-colors" 
            key={user.id}>
            <div>
              <p className="text-white">{user.name}</p>
              <p className="text-gray-400">{user.email}</p>
            </div>
            <button 
              className="opacity-0 group-hover:opacity-100 transition-opacity bg-blue-500 hover:bg-blue-600 text-white py-1 px-3 rounded"
              onClick={() => handleInvite(user.id)}>
              Invite
            </button>
          </li>))}

        </ul>

        <div className="flex flex-nowra gap-2 items-center p-2 rounded-3xl bg-slate-900">
          <input className = "w-full px-2 rounded-xl text-white" type="text" placeholder="Invite participants..."
          value={query}
          onChange={(e) => setQuery(e.target.value)} />
          <MagnifyingGlassIcon className="h-6 w-6 text-gray-500" />
        </div>
      </aside>

    </div>
  );
}

export default IniviteVideo;