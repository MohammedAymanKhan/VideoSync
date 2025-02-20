import { ArrowRightStartOnRectangleIcon } from "@heroicons/react/24/outline";
import { useNavigate } from "react-router-dom";

const GoogleOauth2Code = 'http://localhost:8080/oauth2/authorization/google';

function Login(){

  const navgate = useNavigate();

  function handleGoogleLogin(){
    
    function handleMessage(event) {
      if (event.origin !== "http://localhost:5713") return; 
      
      if(event.data.token){
        navgate('/');
      }   
      else{
        navgate('/login');
      }  

      window.removeEventListener("message", handleMessage);
    }

    const width = 500;
    const height = 600;
    const left = window.screenX + (window.innerWidth - width) / 2;
    const top = window.screenY + (window.innerHeight - height) / 2;

    window.open(
      GoogleOauth2Code,
      'GoogleLogin',
      `width=${width},height=${height},top=${top},left=${left}`
    );

    window.addEventListener("message", handleMessage);
  };


  return (
    <div className="w-screen h-screen flex justify-center items-center bg-neutral-700">

      <div className="glass p-8 w-full max-w-md mx-4 bg-slate-800 rounded-md text-white">

        <h2 className="text-2xl font-semibold text-center mb-8 text-foreground">
          Sign in or Sign up
        </h2>

        <div className="flex gap-4 place-content-center place-items-center">

          <button
            type="button"
            className="px-1 py-2 font-medium flex items-center justify-center gap-2 bg-blue-500 hover:bg-blue-600 rounded-md"
            onClick={() => {handleGoogleLogin()}}>
            <ArrowRightStartOnRectangleIcon className="h-5 w-5" />
            Continue with Google
          </button>

          <div className="text-center">
            <h3 className="text-2xl font-bold text-primary">VideoSync</h3>
            <p className="text-muted-foreground text-sm">
              Connect, Collaborate <br/> and Share Video
            </p>
          </div>

        </div>

      </div>

    </div>
  );
};

export default Login;
