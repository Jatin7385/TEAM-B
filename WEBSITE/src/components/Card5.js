import  React,{useState} from "react";
import './Team.css';
import '../App.css';
import image5 from './images/jatin.jpg';

function Card5() {

    const [name]=useState("Jatin");
    
    const [work]=useState("Responsible for designing the backend and frontend of the GUI that displays plots for carrier and science payload data in real time.");

    return (
        <div className='Card5'>
            <div className='upper-container'>
                <div className='image-container'>
                    <img src= {image5} alt=" " height="6.25rem" width="6.25rem"/>
                </div>
            </div>
            <div className="lower-container">
                <h1>{name}</h1>
               
                <p>{work}</p>
                
            </div>
        </div>
    
  );
}
export default Card5;