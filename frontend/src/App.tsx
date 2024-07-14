import React from "react";
import { HashRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import StudyPlan from "./pages/StudyPlan";
import TeachingClassroom from "./pages/TeachingClassroom";
import UserProfile from "./pages/UserProfile";
import SoftwareDevelopment from "./pages/SoftwareDevelopment";

function App() {
  return (
    <div id={"app"}>
      <HashRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/studyplan" element={<StudyPlan />} />
          <Route path="/teachingroom" element={<TeachingClassroom />} />
          <Route path="/userprofile" element={<UserProfile />} />
          <Route path="/softwaredevelopment" element={<SoftwareDevelopment />} />
        </Routes>
      </HashRouter>
    </div>
  );
}

export default App;
