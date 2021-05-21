import  React, {useState} from 'react';

import ReactCardFlip from "react-card-flip";

const CardFlip1=()=> {
    const [isFlipped,setIsFlipped]=useState (false);
    const handleClick =() =>{
        setIsFlipped(!isFlipped)
    };
    return(
        <>
        <ReactCardFlip isFlipped={isFlipped} flipDirection="vertical">
        <div className="flip" 
        style={{
            background:" #360033",  /* fallback for old browsers */
background: "-webkit-linear-gradient(to bottom, #0b8793, #360033)",  /* Chrome 10-25, Safari 5.1-6 */
background: "linear-gradient(to bottom, #0b8793, #360033)", /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */

            height:450,
            width:330,
            color:"white",
            display:"grid",
            justifyContent:"center",
            alignItems:"center",
            margin:"0 auto",
           fontSize:40,
            transform:" translate(4%, -100%)"
           
        }} >
          LAUNCH
          <button onClick={handleClick}>Click to flip</button>
          </div>

        <div   style={{
         backgroundColor:"black",
         height:450,
         width:330,
         color:"white",
         display:"grid",
         justifyContent:"center",
         alignItems:"center",
         margin:"0  auto",
         transform:" translate(4%, -100%)",
         fontSize:20

         }}>
          <ul>
              <li>Rocket launches with the CANSAT</li>
              <li>Drops the carrier at a height of 2000m</li>
              <li>Wings open to perform helical maneuvers</li>
              <li>Carrier experiences a leveled flight towards east for 300m</li>
              <li>Payload is deployed which comprises of probe and heat shield</li>
              <li>At 500m heat shield seperates from the probe</li>
              <li>Parachute opens for safe landing of the probe</li>
          </ul>
          <button onClick={handleClick}>Click to flip</button>
        
        </div>
        </ReactCardFlip>
        </>
    )
};

export default CardFlip1;
