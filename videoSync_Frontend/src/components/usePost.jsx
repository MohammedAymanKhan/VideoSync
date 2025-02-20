import { useCallback, useState } from "react";
import axios from "axios";

const extractThumbnail = (file) => {
  return new Promise((resolve, reject) => {
    const video = document.createElement('video');
    video.src = URL.createObjectURL(file);
    video.currentTime = 1; // Capture frame at 1 second
    video.addEventListener('loadeddata', () => {
      // Create canvas with video dimensions
      const canvas = document.createElement('canvas');
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      const context = canvas.getContext('2d');
      context.drawImage(video, 0, 0, canvas.width, canvas.height);
      // Convert canvas image to Blob
      canvas.toBlob((blob) => {
        if (blob) {
          resolve(blob);
        } else {
          reject(new Error('Canvas is empty'));
        }
      }, 'image/jpeg', 0.95);
    });
    video.onerror = (err) => reject(err);
  });
};

export default function usePost(url){

  const [response, setResponse] = useState(null); 

  const backendCallback = useCallback(async (formData) =>{

      try{
        const formPayload = new FormData();
        if(formData?.videoFile != undefined){
          const thumbnail = await extractThumbnail(formData.videoFile);

          formPayload.append("videoFile", formData.videoFile); 
          formPayload.append("title", formData.title);
          formPayload.append("thumbnail", thumbnail,'thumbnail.jpg');
          formPayload.append("startTime", formData.startTime);
          formPayload.append("isPublic",formData.isPublic);
        }else{
          formPayload.append('userId',formData.userId);
          formPayload.append('videoId',formData.videoId);
        }

        await axios.post(`http://localhost:8080/api${url}`, formPayload, {
          headers:{
            "Authorization": `Bearer ${localStorage.getItem("token")}`,
          }
        });

        setResponse({ 
          status: true, 
          message: 'successfully!' 
        });
      }catch{
        setResponse({ 
          status: false, 
          message: 'failed' 
        });
      }

  } ,[url]);

  return [response, setResponse, backendCallback];
}



