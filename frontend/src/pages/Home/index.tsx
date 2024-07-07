import React from "react";
import NavHeader from "../../components/NavHeader";
import "./index.less";

const Home = () => {
  return (
    <>
      <div className="xf-home__page">
        <header className="xf-home__header">
          <NavHeader />
        </header>
        <main className="xf-home__main"></main>
        <footer className="xf-home__footer"></footer>
      </div>
      <div className="xf-home__mask"></div>
    </>
  );
};

export default Home;
