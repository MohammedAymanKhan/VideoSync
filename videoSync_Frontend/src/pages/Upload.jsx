import { useState } from "react";
import { CalendarIcon } from "@heroicons/react/24/outline";
import usePost from "../components/usePost";
import responseDisplay from "../components/responseDisplay";

function getLocalDateTime() {
  const date = new Date();
  const offset = date.getTimezoneOffset();
  date.setMinutes(date.getMinutes() - offset);
  return date.toISOString().slice(0, 16);
}

function Upload(){

  const [selectedFile, setSelectedFile] = useState(null);
  const [formData, setFormData] = useState({
    title: '',
    startTime: getLocalDateTime(),
    isPublic: true,
  });
  const [response, setResponse, postVideo] = usePost('/videos/upload');

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleFileDrop = (e) => {
    e.preventDefault();
    const file = e.dataTransfer.files[0];
    if (file && file.type.startsWith('video/')) {
      setSelectedFile(file);
    }
  };

  const handleFileSelect = (e) => {
    const file = e.target.files[0];
    if (file && file.type.startsWith('video/')) {
      setSelectedFile(file);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const formPayload = {
      ...formData,
      "videoFile": selectedFile
    }
    postVideo(formPayload);
    setSelectedFile(null);
    setFormData({
      title: '',
      startTime: getLocalDateTime(),
      isPublic: true,
    });
  };


 
  return (
    <>
    <div className="relative max-w-2xl mx-auto p-6 animate-fadeIn">

      {response != null && responseDisplay(response.status,['Video uploaded successfully','Upload failed. Please try again'],setResponse)}

      <h2 className="text-2xl font-semibold mb-6 text-white">Upload Video</h2>

      <form onSubmit={handleSubmit} className="space-y-6">
        <div className="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-400 transition-colors"
          onDragOver={(e) => e.preventDefault()}
          onDrop={handleFileDrop}>

          {selectedFile ? (
            <div className="space-y-2">
              <p className="text-sm text-gray-300">Selected file:</p>
              <p className="text-blue-400 font-medium">{selectedFile.name}</p>
            </div>
          ) : (
            <div className="space-y-4">
              <p className="text-gray-300">Drag and drop your video here, or</p>
              <input
                type="file"
                accept="video/*"
                onChange={handleFileSelect}
                className=" p-2 bg-gray-800 text-white block w-full text-sm border border-gray-300 rounded-lg cursor-pointer focus:outline-none"
              />
            </div>
          )}

        </div>

        <div className="space-y-4">
          <div className="space-y-2">
            <label
              htmlFor="title"
              className="block text-sm font-medium text-gray-300">
              Title
            </label>
            <input
              id="title"
              name="title"
              type="text"
              placeholder="Enter video title"
              value={formData.title}
              onChange={handleInputChange}
              className="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm text-white"
              required
            />
          </div>

          <div className="space-y-2">
            <label
              htmlFor="startTime"
              className="block text-sm font-medium text-gray-300"
            >
              Start Time
            </label>
            <div className="relative">
              <input
                id="startTime"
                name="startTime"
                type="datetime-local"
                min={getLocalDateTime()}
                value={formData.startTime}
                onChange={handleInputChange}
                className="block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm text-white"
                required
              />
              <CalendarIcon className="absolute pointer-events-none right-3 top-2.5 h-5 w-5 text-white" />
            </div>
          </div>

          <div className="flex items-center justify-between">
            <label
              htmlFor="isPublic"
              className="text-sm font-medium text-gray-300">
              Make this video public
            </label>
            <input
              id="isPublic"
              name="isPublic"
              type="checkbox"
              checked={formData.isPublic}
              onChange={handleInputChange}
              className="h-5 w-5 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
            />
          </div>
        </div>

        <button
          type="submit"
          className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-500 hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
          Upload Video
        </button>
      </form>
    </div>
  </>);
};

export default Upload;
