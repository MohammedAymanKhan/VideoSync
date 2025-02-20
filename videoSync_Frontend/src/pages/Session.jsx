import {UserIcon, PaperAirplaneIcon} from "@heroicons/react/24/outline";
import { useContext, useEffect, useRef, useState } from "react";
import useFetch from "../components/useFetch";
import { useParams } from "react-router-dom";
import { getContext } from "../components/AuthContext";
import { Stomp } from '@stomp/stompjs';

function Session(){
  const { videoId } = useParams();
  const videoRef = useRef(null);
  const msgInputRef = useRef(null);
  const authUser = JSON.parse(localStorage.getItem('authUser'));
  const[video, setVideo, getVideo] = useFetch(`/videos/video/${videoId}`);
  const[changedUser, setChangesUser] = useState(null);
  const [newMessage, setNewMessage] = useState(null);
  const client = useRef(null);

  useEffect(()=>{
    getVideo();
  },[videoId]);
  
  

  useEffect(() => {
    if(video != null){
      client.current = Stomp.over(()=>new WebSocket(`ws://localhost:8080/syncChanges?token=${localStorage.getItem('token')}`));
      client.current.connect({}, onConnected, onError);

      function onConnected(){
        client.current.subscribe(`/videoSync/${video.inviteKey}`,videoSyncSocket);
        client.current.subscribe(`/chatSync/${video.inviteKey}`, chatManagement);
      }

      function videoSyncSocket(message){
        const payload = JSON.parse(message.body);
        setChangesUser({
          name: payload.userName,
          action: payload.type
        });
        if(payload.userId == authUser.id) return;
        switch (payload.type) {
          case 'pause': {
            videoRef.current.currentTime = payload.timeStamp;
            videoRef.current.pause();
            break;
          }
          case 'play': {
            videoRef.current.currentTime = payload.timeStamp;
            videoRef.current.play();
            break;
          }
          case 'seeked': {
            videoRef.current.currentTime = payload.timeStamp;
            break;
          }
          default:
            console.warn('Unhandled message type:', payload.type);
        }
      }
      function chatManagement(message){
        const messagepayload = JSON.parse(message.body);
        setNewMessage(messagepayload);
      }

      function onError(error){
        console.log('Error Message: '+error);
      }
  }}, [video]);
  
  const sendVideoSync = (type, timeStamp) =>{
    client.current.publish(
      {
        destination: `/videoSync/${video.inviteKey}`,
        body: JSON.stringify({ 
          userId: authUser.id,
          userName: authUser.name,
          type: type, 
          timeStamp : timeStamp}),
      }
    );
  }

  const sendChatMsg = () => {
    client.current.send(`/app/chat`, {'chatRoomId': video.inviteKey}, JSON.stringify({
      videoId: video.id,
      senderId: authUser.id,
      name: authUser.name,
      message: msgInputRef.current.value
    }));
  }

  if(video == null){
    return <h2>Loading</h2>;
  }


  return(
    
    <div className = 'flex gap-6 w-dvw h-dvh bg-gray-900' key={video.id}>

      <section className = 'flex flex-col justify-between content-between basis-7/10'>
        <header className = 'flex justify-between items-center py-2 px-4  bg-gray-950 rounded-md text-white h-1/10'>
          <h2 className = 'text-2xl font-bold'>{video.title}</h2>
          <button className = 'bg-red-500 rounded-md px-4 py-2'>Leave</button>
        </header>

        <div className=" relative h-8/10 aspect-video flex items-center justify-center">
          <video
            ref={videoRef}
            src={`data:video/mp4;base64,${video.videoData}`} 
            type='video/mp4'
            controls
            onPause={(event)=>sendVideoSync(event.type, event.timeStamp)}
            onPlay={(event)=>sendVideoSync(event.type, event.timeStamp)}
            onSeeked={(event)=>sendVideoSync(event.type, event.timeStamp)}
            className = 'aspect-video rounded-sm h-8/10'
            />
        </div>

        <div className = 'flex justify-between px-2 py-4 rounded-md bg-gray-950 text-white h-1/10 text-sm'>
            <div className = 'flex gap-2 items-center text-md'>
              <UserIcon className=" w-7 h-7 text-black rounded-full bg-blue-500 p-1" />
              {authUser.name}
            </div>

            {changedUser != null &&
            <div className = 'flex gap-2 items-center'>
              <span className = 'font-bold'>{`Video ${changedUser.action} by:`}</span> 
              <div className = 'flex gap-2 items-center'>
                <UserIcon className=" w-7 h-7 text-black rounded-full bg-blue-500 p-1" />
                {changedUser.name}
              </div>
            </div>}

        </div>
      </section>

      <section className = 'basis-3/10 m-2 flex flex-col gap-2 rounded-xl bg-gray-950'>
        
        <header className="flex text-whit text-white m-1 px-2 py-3 justify-between items-center gap-2 bg-gray-900 rounded-t-xl">
          <h3 className="font-bold">Group Chat:</h3>
          <h3 className="bg-gray-950 p-2 rounded-xl hover:bg-gray-800">Messages</h3>
          <h3 className="bg-gray-950 p-2 rounded-xl hover:bg-gray-800">Participants</h3>
        </header>

        <ul className="flex flex-col gap-4 text-white h-8/10 text-[0.75rem] overflow-y-scroll scrollbar-thin">
          {video.chats.map(chat => (
            chat.senderId != authUser.id ?
            (<li className="flex flex-col items-start justify-center bg-gray-800 px-3 py-2 rounded-2xl w-fit min-w-55 mx-3" key={chat.id}>
              <p className="font-bold" key={chat.senderId}>{chat.senderName}</p>
              <p className="text-slate-400">{chat.message}</p>
            </li>) :
            (<li className="flex flex-col items-start justify-center bg-gray-800 px-3 py-2 rounded-2xl w-fit min-w-55 mx-3 self-end" key={chat.id}>
              <p className="font-bold"  key={chat.senderId}>You</p>
              <p className="text-slate-400">{chat.message}</p>
            </li>)
          ))}
          {newMessage != null && (newMessage.sender.id != authUser.id ?
          (<li className="flex flex-col text-sm items-start justify-center bg-gray-800 px-3 py-2 rounded-md m-2 w-fit min-w-55" key = {newMessage.id}>
            <p className="font-bold" key={newMessage.sender.id}>{newMessage.sender.name}</p>
            <p className="text-slate-400">{newMessage.message}</p>
          </li>) :
          (<li className="flex flex-col items-start justify-center bg-gray-800 px-3 py-2 rounded-2xl w-fit min-w-55 mx-3 self-end" key={newMessage.id}>
            <p className="font-bold"  key={newMessage.sender.id}>You</p>
            <p className="text-slate-400">{newMessage.message}</p>
          </li>))}
        </ul>


        <div className="flex content-center gap-3 p-1 m-0.5 bg-gray-900 rounded-b-xl">
          <input ref = {msgInputRef} type="text" placeholder="Type a message..." className="text-white placeholder:text-slate-600 w-full bg-gray-950 p-2 rounded-md"/>
          <button onClick={()=>{sendChatMsg()}} className="hover:bg-gray-950 p-1 rounded-sm">
            <PaperAirplaneIcon className="h-6 w-6 text-gray-500" />
          </button>
        </div>
      </section>

    </div>


  );

}

export default Session;