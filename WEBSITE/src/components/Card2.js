import  React,{useState} from "react";
import './Team.css';
import '../App.css';
import image2 from './images/siya.jpg';

function Card2() {

    const [name]=useState("Siya");
    
    const [work]=useState("Responsible for designing the front end of the CANSAT mission website using React js and Three.js library and storing all the files in the github repository.");

    return (
        <div className='Card2'>
            <div className='upper-container'>
                <div className='image-container'>
                    <img src= {image2} alt=" " height="6.25rem" width="6.25rem"/>
                </div>
            </div>
            <div className="lower-container">
                <h1>{name}</h1>
               
                <p>{work}</p>
                
            </div>
        </div>
    
  );
}
export default Card2;