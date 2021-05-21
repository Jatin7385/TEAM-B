import React from 'react'; 
import { Animator, ScrollContainer, ScrollPage, batch, Fade, FadeIn, Move, MoveIn, MoveOut, Sticky, StickyIn, ZoomIn } from "react-scroll-motion";
import airspeedsensor from './images/Air speed sensor.jpg';
import XBee from './images/XBee.jpg';
import IMU from './images/IMU.jpg';
import Microcontroller from './images/Microcontroller.jpg';
import GPS from './images/GPS.jpg';
import PCBC from './images/PCB Carrier.PNG';
import PCBP from './images/PCB Probe.PNG';
import payload from './images/payload.PNG';
import propeller from './images/propeller.jpg';
import heatshield from './images/heat shield.PNG';
import parachute1 from './images/parachute1.jpg';
import wing from './images/wing.jpg';
import buzzer from './images/buzzer main.png';

const Presenter = () => {
  
  const ZoomInScrollOut = batch(StickyIn(), FadeIn(), ZoomIn());
  const FadeUp = batch(Fade(), Move(), Sticky());

  return (
   
    <ScrollContainer>
      <ScrollPage page={0}>
        <Animator animation={batch(Fade(), Sticky(), MoveOut(0, -200))}>
            <div style={{columnCount:"3", columnGap:"3.75rem"}}>
            <img src={airspeedsensor} width="331" height="240" />
        <p style={{ fontSize: "1.3em", color:"blue" , textAlign:"center"}}>AIR SPEED SENSOR</p>
          <p style={{ fontSize: "1.2em", color:"white", textAlign:"center" }}>Airspeed Meter helps in heavy wind, slow speed flying and automatic landing.Working principle is upper tube is dynamic (measuring the air pressure of front opening, air is compressed into airspeed tube), the bottom tube is static (measuring the side port airspeed tube).</p>
          <img src={XBee} width="331" height="240" />
          <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center"}}>XBee</p>
          <p style={{ fontSize: "1.2em", color:"white", textAlign:"center"}}>With a 300ft indoor range, or 2 mile outdoor/line-of-sight range, you can set up a mesh network to talk to or communicate with various devices around your house, work, or other area.This module has the XBee package while sporting a connector to add an RP-SMA antenna.</p>
          <img src={IMU} width="331" height="240" />
          <p style={{ fontSize: "1.4em", color:"blue" , textAlign:"center"}}>IMU FOR DETERMINING THE ORIENTATION</p>
          <p style={{ fontSize: "1.2em", color:"white", textAlign:"center"}}>This module is base on MPU-9250 and BMP280, the MPU-9250 is a 9-axis MotionTracking device that combines a 3-axis gyroscope, 3-axis accelerometer, 3-axis magnetometer and a Digital Motion Processor and BMP280 is a high precision, ultra-low power digital pressure sensors for consumer applications.</p>

</div>
        </Animator>
      </ScrollPage>
      <ScrollPage page={1}>
        <Animator animation={ZoomInScrollOut}>
        <div style={{columnCount:"2", columnGap:"5rem"}}>
        <img src={Microcontroller} width="320" height="235" />  
        <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center"}}>MICROCONTROLLER</p>
          <p style={{ fontSize: "1.2em" , color:"white", textAlign:"center" }}>The Arduino Pro Mini is intended for semi-permanent installation in objects or exhibitions. The board comes without pre-mounted headers, allowing the use of various types of connectors or direct soldering of wires. The pin layout is compatible with the Arduino Mini.</p>
          <img src={GPS} width="331" height="240" />
          <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center"}}>GPS</p>
          <p style={{ fontSize: "1.2em" , color:"white" , textAlign:"center"}}>GPS modules NEO-6M has 3V-5V power supply with ceramic destined antenna and super signal.It consists of LED signal indicator with data backup battery </p>
          </div> 
        </Animator>
      </ScrollPage>
      <ScrollPage page={2}>
        <Animator animation={FadeUp}>
        <div style={{columnCount:"2", columnGap:"5rem"}}>
        <img src={PCBC} width="330" height="269" />  
        <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center"}}>PCB design for the carrier</p>
          <img src={PCBP} width="330" height="269" />
          <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center"}}>PCB design for the probe</p>
          </div> 
        </Animator>
      </ScrollPage>
      <ScrollPage page={3}>
      
      <Animator animation={batch(Fade(), Sticky(), MoveOut(0, -200))}>
        <div style={{columnCount:"3", columnGap:"1rem"}}>
             <img src={heatshield} style={{width:"22rem", height:"19rem", marginLeft:"4rem" }} />  
         <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center"}}>HEAT SHIELD</p>
        <p style={{ fontSize: "1.34em", color:"white", textAlign:"center" }}>Heat shield is required to reduce the velocity so that the probe's sensors can record accurate values.It descends at a rate of 15 m/s and maintains its nadir.</p>
        <img src={parachute1}style={{width:"22rem", height:"19rem",marginLeft:"4rem" }} />  
            <p style={{ fontSize: "1.4em", color:"blue",textAlign:"center"}}>PARACHUTE</p>
            <p style={{ fontSize: "1.34em", color:"white", textAlign:"center" }}>
          It is attached to the upper part of the probe due to which it experiences a controlled descent of 5m/s
           </p> 
           <img src={wing}style={{width:"22rem", height:"19rem", marginLeft:"4rem" }} />  
        <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center"}}>WING DESIGN</p>
        <p style={{ fontSize: "1.34em", color:"white", textAlign:"center" }}>
          These wings are deployed from the carrier once it reaches a height of 2000m. The purpose of these wings is to make the carrier perform helical maneuverfor 500m from the release point.
        </p> 
        </div>
            </Animator>
     </ScrollPage>
      <ScrollPage page={4}>
        <Animator animation={batch(Fade(), Sticky())}>
        <div style={{columnCount:"3", columnGap:"5rem"}}>
        <img src={propeller} width="330" height="269" />
          <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center" }}>PROPELLER</p>
          <p style={{ fontSize: "1.3em" , color:"white" , textAlign:"center" }}> It is attached on top of the carrier so that it can perform powered flight.It provides a method of propulsion so that it is able to move forward through the air. 
          </p>
          <img src={payload} width="330" height="269" />
          <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center" }}>SCIENCE PAYLOAD</p>
          <p style={{ fontSize: "1.2em" , color:"white" , textAlign:"center" }}>The scientific payload detects the local weather data which includes air temperature, altitude and air speed.It consists of a heat shield which carries the probe with all the equipment and a hen's egg.
          </p>
          <img src={buzzer} width="330" height="269" />
          <p style={{ fontSize: "1.4em", color:"blue", textAlign:"center" }}>BUZZER</p>
          <p style={{ fontSize: "1.3em" , color:"white" , textAlign:"center" }}>It is placed on the carrier and starts ringing after landing to identify its position.
          </p>
        </div>
        </Animator>
      </ScrollPage>
     </ScrollContainer>
  );
};
export default Presenter;
