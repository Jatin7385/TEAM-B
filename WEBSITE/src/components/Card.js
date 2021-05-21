import  React,{useState} from "react";
import './Team.css';
import '../App.css';
import image from './images/supreeth.jpg';

function Card() {

    const [name]=useState("Supreeth");
    
    const [work]=useState("Responsible for selecting the sensors and programming both the carrier and the probe. He played an active role in callibrating the sensors and completely understanding the function of Xbee as well as programming it.");

    return (
        <div className='Card'>
            <div className='upper-container'>
                <div className='image-container'>
                    <img src= {image} alt=" " height="6.25rem" width="6.25rem"/>
                </div>
            </div>
            <div className="lower-container">
                <h1>{name}</h1>
               
                <p>{work}</p>
                
            </div>
        </div>
    
  );
}
export default Card;