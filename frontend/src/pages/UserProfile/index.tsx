import React from "react";
import "./index.less";
import NavHeader from "../../components/NavHeader";

const UserProfile = () => {
  return (
    <>
      <div className="xf-user__page">
        <header className="xf-user__header">
          <NavHeader />
        </header>
        <main className="xf-user__main"></main>
        <footer className="xf-user__footer"></footer>
      </div>
      <div className="xf-user__mask"></div>
    </>
  );
};

export default UserProfile;
