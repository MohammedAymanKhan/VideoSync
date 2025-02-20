import { useCallback, useState } from "react";

export default function useFetch(url, methodType = 'GET'){

  const [response, setResponse] = useState(null); 
  const backendCallback = useCallback(async (context = null) =>{
      const response = await fetch(`http://localhost:8080/api${url}`,{
        method : methodType,
        headers : { 
          Authorization: `Bearer ${localStorage.getItem('token')}`,
          "Accept": "application/json",
        } 
      });

      const data = await response.json();
      setResponse(data);
      if(context != null){
        context.setAuthUser(data);
        localStorage.setItem('authUser', JSON.stringify(data));
      } 
  } ,[url, methodType]);

  return [response, setResponse, backendCallback];
}



