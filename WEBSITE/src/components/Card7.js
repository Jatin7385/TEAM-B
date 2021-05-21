import  React,{useState} from "react";
import './Team.css';
import '../App.css';
import image7 from './images/aaron.jpg';

function Card7() {

    const [name]=useState("Aaron");
    
    const [work]=useState("Responsible for designing the carrier as well as the heat shield to reduce the velocity of the payload so the probe sensors can record accurate values.");
    

    return (
        <div className='Card7'>
            <div className='upper-container'>
                <div className='image-container'>
                    <img src= {image7} alt=" " height="6.25rem" width="6.25rem"/>
                </div>
            </div>
            <div className="lower-container">
                <h1>{name}</h1>
               
                <p>{work}</p>
                
            </div>
        </div>
    
  );
}
export default Card7;