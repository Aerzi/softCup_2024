import React from "react";
import { HashRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import StudyPlan from "./pages/StudyPlan";
import TeachingClassroom from "./pages/TeachingClassroom";
import UserProfile from "./pages/UserProfile";
import MockCompetition from "./pages/MockCompetition";

function App() {
  return (
    <div id={"app"}>
      <HashRouter>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/studyplan" element={<StudyPlan />} />
          <Route path="/teachingroom" element={<TeachingClassroom />} />
          <Route path="/userprofile" element={<UserProfile />} />
          <Route path="mockcompetition" element={<MockCompetition />} />
        </Routes>
      </HashRouter>
    </div>
  );
}

export default App;
