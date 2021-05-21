import  React,{useState} from "react";
import './Team.css';
import '../App.css';
import image4 from './images/shreyas.jpg';

function Card4() {

    const [name]=useState("Shreyas");
    
    const [work]=useState("He worked on understanding and implementing the mechanism required for deploying the wings from the carrier.He also worked on the mechanism to deploy the parachute from the probe for its safe landing");

    return (
        <div className='Card4'>
            <div className='upper-container'>
                <div className='image-container'>
                    <img src= {image4} alt=" " height="6.25rem" width="6.25rem"/>
                </div>
            </div>
            <div className="lower-container">
                <h1>{name}</h1>
                
                <p>{work}</p>
                
            </div>
        </div>
    
  );
}
export default Card4;