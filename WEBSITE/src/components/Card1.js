import  React,{useState} from "react";
import './Team.css';
import '../App.css';
import image1 from './images/chandrashish.jpg';

function Card1() {

    const [name]=useState("Chandrashish");
    
    const [work]=useState("Responsible for selecting the sensors and designing the PCB for both the carrier and the probe. He played an active role in callibrating the sensors and completely understanding the function of Xbee as well as programming it.");

    return (
        <div className='Card1'>
            <div className='upper-container'>
                <div className='image-container'>
                    <img src= {image1} alt=" " height="6.25rem" width="6.25rem"/>
                </div>
            </div>
            <div className="lower-container">
                <h1>{name}</h1>
               
                <p>{work}</p>
                
            </div>
        </div>
    
  );
}
export default Card1;