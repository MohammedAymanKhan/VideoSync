
import { XMarkIcon } from "@heroicons/react/24/outline";

export default function responseDisplay(status = false, message = 'failed', setStatus){

 return(
  <>
    {status ? 
      <div className="absolute top-0 left-50 z-20 flex items-center bg-green-200 text-green-900 rounded-lg py-3 px-10 w-fit">
        <span>{message[0]}</span>
        <button onClick={() => setStatus(null)} className="hover:text-gray-900 ml-2">
          <XMarkIcon className="h-5 w-5 text-gray-600" />
        </button>
      </div> :

      <div className="absolute top-0 left-50 z-20 flex items-center bg-red-200 text-red-900 rounded-lg pt-2.5 pb-3.5 px-10 w-fit">
        <span>{message[1]}</span>
        <button onClick={() => setStatus(null)} className="ml-2 hover:text-gray-900">
          <XMarkIcon className="h-5 w-5 text-gray-600" />
        </button>
      </div>}
  </>
 );

}