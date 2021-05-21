import  React, {useState} from 'react';

import ReactCardFlip from "react-card-flip";

const CardFlip2=()=> {
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
            transform:" translate(178%, -200%)"
           
        }} >
         POST LAUNCH
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
         transform:" translate(178%, -200%)",
         fontSize:27.5

         }}>
          <ul>
              
                 <li>Data from carrier and payload is retrieved and sent to ground station</li>
                 <li>Data is sent in the form of packets </li>
                 <li>Real time graphs are then plotted with the established GUI</li>

          </ul>
          <button onClick={handleClick}>Click to flip</button>
        
        </div>
        </ReactCardFlip>
        </>
    )
};

export default CardFlip2;








