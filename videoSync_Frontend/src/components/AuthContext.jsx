import { createContext, useContext, useState } from "react"

const AuthContext = createContext(null); 

function getContext(){
  const UserContent = useContext(AuthContext);
  return UserContent;
}

export {AuthContext, getContext};