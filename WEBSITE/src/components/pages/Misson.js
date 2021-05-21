import React from 'react';

import '../../App.css';
import Mission1 from '../Mission1';
import FlipCard1 from '../FlipCard1';
import FlipCard2 from '../FlipCard2';
import FlipCard3 from '../FlipCard3';
import Video from '../Video';
import Table1 from '../Table1';
import Footer from '../Footer';
import Table2 from '../Table2';



function Mission() {
    return (
      <>
        <Mission1/>
        <h1 style={{color:"white"}}>CONOPS</h1>
        <Video/>
        <br/><br/> <br/>
        <FlipCard1/>       
        <FlipCard2/>
        <FlipCard3/>
        <h1 style={{fontFamily:"cursive", fontSize:"45px",position:"absolute",top:"237%", color:"white", right:"32%", color:"#7ed5f7"}}>PROBE CONSTRAINTS</h1>
        
        <Table1/>
        <h1 style={{fontFamily:"cursive", fontSize:"45px",textAlign:"center", color:"white", color:"#7ed5f7"}}>CARRIER CONSTRAINTS</h1>
        <Table2/>
       
        <Footer/> 
       </>
    );
  }
  
  export default Mission;