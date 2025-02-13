import { useLocation, useNavigate} from 'react-router-dom';

function OAuthTokenRedirect(){
  const location = useLocation();
  const navgation = useNavigate();

  const queryParams = new URLSearchParams(location.search);

  if(queryParams.get('token') != undefined){
    localStorage.setItem('token',queryParams.get('token'));
    window.opener.postMessage({token: true},"http://localhost:5713");
    window.close();
  }else{
    const error = queryParams.get('error');
    localStorage.setItem('token',null);
    window.opener.postMessage({token: false},"http://localhost:5713");
    window.close();
  }
  

  return <div>Processing authentication...</div>;
}

export default OAuthTokenRedirect;