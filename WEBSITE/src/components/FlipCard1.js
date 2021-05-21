import  React, {useState} from 'react';

import ReactCardFlip from "react-card-flip";

const CardFlip=()=> {
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
           fontSize:40,
           fontWeight:"90px"
        }} >
        PRE LAUNCH
          <button onClick={handleClick}>Click to flip</button>
          </div>

        <div className="flip"  style={{
         backgroundColor:"black",
         height:450,
         width:330,
         color:"white",
         display:"grid",
         justifyContent:"center",
         alignItems:"center",
         fontSize:23,
           fontWeight:"90px"
         }}>
          <ul>
              <li>Set up ground station</li>
              <li>Check mechanical systems</li>
              <li>Communication tests should be conducted</li>
              <li>The system will be callibrated with the command sent to the ground station and data tranfer will be started </li>
              <li>CANSAT will be placed in rocket</li>
          </ul>
          <button onClick={handleClick}>Click to flip</button>
        
        </div>
        </ReactCardFlip>
        </>
    )
};

export default CardFlip;
