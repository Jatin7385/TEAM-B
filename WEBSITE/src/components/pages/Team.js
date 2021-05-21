import React from 'react';
import Card from '../Card';
import Card1 from '../Card1';
import Card2 from '../Card2';
import Card3 from '../Card3';
import Card4 from '../Card4';
import Card5 from '../Card5';
import Card6 from '../Card6';
import Card7 from '../Card7';
import MyCarousel from '../Mentors';
import Footer from '../Footer';

function Team() {
  return (
    <>
     <div className="teamBack"></div>
     <h1 style={{textAlign:"center",color:"darkblue"}}>MEET OUR TEAM</h1>
      <hr/><br/>
      <h2 style={{textAlign:"center" ,color:"darkblue",fontSize:35}}>ELECTRONICS TEAM</h2>
      <Card/>
      <Card1/>
      <br/>   <br/>   <br/>
      <h2 style={{marginLeft:"6.5rem" ,color:"darkblue",fontSize:35}}>FRONT END WEB DEVELOPER</h2>
      <Card2/>
      <br/><br/>   <br/>
      <h2 style={{marginLeft:"63.75rem", marginTop:"-35.3rem" ,color:"darkblue",fontSize:35}}>GUI DESIGNER</h2>
      <Card5/>
      <br/><br/>   <br/>
      <h2 style={{textAlign:"center" ,color:"darkblue",fontSize:35}}>MECHANICAL TEAM</h2>
      <Card3/>
      <Card4/>
      <Card6/>
      <Card7/>
      <br/><br/><br/><br/><br/><br/>
      <div className="neon1">
  <span className="text1" >Special thanks to our mentors for their </span>
  <span className="text2" >constant guidance and support </span>
  <span className="gradient1"></span>
  <span className="spotlight1"></span>
</div>
      <MyCarousel/>
      <Footer/>
     
    </>
  );
}

export default Team;


