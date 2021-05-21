import React from 'react';
import './Mission.css';
import rocket from './images/rocket.jpg';
import {Fade} from "react-awesome-reveal";


function MissionStatement(){
    return(
        <>
        <img src= {rocket} alt=" " height="580px" width="1516px"/>
         

        <h1 style={{textAlign:"center",color:"black",fontSize:42,
   fontweight:" bold",bottom:"75%",left:"38%",position:"absolute"}}>MISSION OVERVIEW</h1>
   <div className="beautify">
   <Fade><h2 className="enhance">The carrier of the main payload is to be loaded in the rocket.It is released at the altitude of 2000 meters from the launch site.The carrier is supposed to perform helical maneuver for 500 meters from the release point.Once the carrier starts performing helical motion, it should transmit data to the ground station. On attaining an altitude of 1500 meters the carrier will start a powered flight towards East for 300 meters.Once it reaches the distance, carrier should drop a payload which should be able to detect the local weather data which includes air temperature,altitude and air speed.The science payload consiste of a heat shield which will carry the probe with all the equipment and the hen's egg.Once the heat shield attains an altitude of 500 meters, it should release the probe with the egg. The probe will descend using a parachute. The carrier should land in gliding state.</h2></Fade>
   </div> 
        
        </>
        );
}

export default MissionStatement;