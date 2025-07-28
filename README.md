# VideoSync 🎥

**Watch videos together, in sync, from anywhere.**

---
## Table of Contents

- [About](#about)
- [Problem Solved](#problem-solved)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Demo](#demo)
- [Installation](#installation)

---

## About

VideoSync is a real-time video synchronization platform that lets you watch videos with others, with all interactions—like play, pause, and skip—perfectly synced across everyone in the room. It also includes a chat feature so you can discuss what’s on screen as it happens. Whether you’re hosting a movie night, sharing a family video, or studying with classmates, VideoSync makes it feel like you’re all together, no matter the distance.

---

## Problem Solved

Have you ever wanted to watch a pre-recorded video—like a movie, a family memory, or a study session—with friends or family far away, as if it were live? Normally, you’d struggle to keep everyone in sync, with awkward pauses or mismatched timing ruining the experience. VideoSync fixes this by syncing every second of the video for all users in real-time, complete with a chat feature to talk about it as you watch—whether it’s a private group or a public event anyone can join.

---

## Features

- **Upload & Schedule:** Upload a video, set a date and time, and choose if it’s public or private.
- **Public or Private Access:**
  - *Public:* Anyone can request to join, pending host approval.
  - *Private:* Only invited users can participate.
- **Invite Users:** Search for people by name or email and send them invites to your video room.
- **Synchronized Watching:** Everyone sees the same moment of the video—play, pause, or skip, and it updates for all in real-time.
- **Real-Time Chat:** Chat with everyone in the room, keeping discussions tied to what’s on screen.

---

## Technologies Used

![React](https://img.shields.io/badge/-React-61DAFB?logo=react&logoColor=white&style=flat)
![Tailwind CSS](https://img.shields.io/badge/-Tailwind_CSS-38B2AC?logo=tailwind-css&logoColor=white&style=flat)
![Spring Boot](https://img.shields.io/badge/-Spring_Boot-6DB33F?logo=spring-boot&logoColor=white&style=flat)
![MySQL](https://img.shields.io/badge/-MySQL-4479A1?logo=mysql&logoColor=white&style=flat)

- **Frontend:** React, Tailwind CSS
- **Backend:** Spring Boot, Spring WebSocket, Spring JPA, Spring Security
- **Database:** MySQL

---

## Demo
Here’s VideoSync in action!

### Uploading & Scheduling a Video

https://github.com/user-attachments/assets/ce6ca56e-7f28-4ff6-bdf2-4e8a4a9e00b2

### User Invite by Name or email

https://github.com/user-attachments/assets/77d169ff-afd8-4c9c-8ed8-5415367303f2

### Synchronized Playback with Chatting

https://github.com/user-attachments/assets/5f061b31-b335-455e-9b53-360f960fe411

---

## Installation

Want to try VideoSync locally? Here’s how:

# Prerequisites
- **Node.js & npm** (v16+)
- **Java JDK 17+**  
- **Maven**  
- **MySQL** (v8+)
- **Google OAuth credentials (Google Cloud Console)**

---

# 1. Clone the repo
```bash git clone https://github.com/MohammedAymanKhan/VideoSync.git```

1. **Spring Boot Setup**  
   - All dependencies are already included.  
   - Simply **reload Maven/Gradle** in your IDE to resolve them.

2. **Frontend (React + TailwindCSS)**  
   - Navigate to the React app folder:  
     ```bash
     cd frontend
     npm install
     npm run dev
     ```

3. **Google OAuth (Authentication)**  
   - Configure your `application.properties` with:
     ```properties
     spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
     spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
     ```

4. **Database Setup**  
   - Update `application.properties` with your DB credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DB_NAME
     spring.datasource.username=YOUR_USERNAME
     spring.datasource.password=YOUR_PASSWORD
     ```

You're now ready to run the app! 🎉
