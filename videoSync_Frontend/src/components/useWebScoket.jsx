import { useRef, useCallback } from "react";
import { Client } from '@stomp/stompjs';

function useWebSocket(video){
  const client = useRef(null);
  const backendCallback = useCallback((videoRef)=>{
    client.current = new Client({
      brokerURL: `ws://localhost:8080/syncChanges?token=${localStorage.getItem('token')}`,
      reconnectDelay: 5000,  
      onConnect: (frame) => {
        console.log('Succefully Connected');
        client.current.subscribe(`/videoSync/${video.inviteKey}`, (message) => {
          const payload = JSON.parse(message.body);
          console.log(videoRef.current);
          console.log('Received message:', payload);
          switch (payload.type) {
            case 'pause': {
              videoRef.current.pause();
              videoRef.current.currentTime = payload.timeStamp;
              break;
            }
            case 'play': {
              videoRef.current.currentTime = payload.timeStamp;
              videoRef.current.current.play();
              break;
            }
            case 'seeked': {
              videoRef.current.current.currentTime = payload.timeStamp;
              break;
            }
            default:
              console.warn('Unhandled message type:', payload.type);
          }
        });
      },
      onStompError: (frame) => {
        console.error('STOMP Error:', frame);
      }
    });

    client.current.activate();

    return client.current;
  },[video]);

  return backendCallback;
}

export default useWebSocket;