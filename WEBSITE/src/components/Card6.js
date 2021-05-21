import  React,{useState} from "react";
import './Team.css';
import '../App.css';
import image6 from './images/farhan.jpg';

function Card6() {

    const [name]=useState("Farhan");
    
    const [work]=useState("Responsible for working on the powered flight of the carrier towards east maintaining the altitude at 1500 meters from the ground.");

    return (
        <div className='Card6' >
            <div className='upper-container'>
                <div className='image-container'>
                    <img src= {image6} alt=" " height="6.25rem" width="6.25rem"/>
                </div>
            </div>
            <div className="lower-container">
                <h1>{name}</h1>
               
                <p>{work}</p>
                
            </div>
        </div>
    
  );
}
export default Card6;