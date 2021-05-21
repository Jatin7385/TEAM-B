import  React,{useState} from "react";
import './Team.css';
import '../App.css';
import image3 from './images/supreet.jpg';

function Card3() {

    const [name]=useState("Supreet");
    
    const [work]=useState("Responsible for understanding the mechanism required by the carrier for performing helical maneuvers and designing wings for the same.She also contributed in designing the carrier as well as the heat shield for the scientific payload");

    return (
        <div className='Card3'>
            <div className='upper-container'>
                <div className='image-container'>
                    <img src= {image3} alt=" " height="6.25rem" width="6.25rem"/>
                </div>
            </div>
            <div className="lower-container">
                <h1>{name}</h1>
                
                <p>{work}</p>
                
            </div>
        </div>
    
  );
}
export default Card3;