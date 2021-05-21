import React from 'react';
import CardItem from './CardItem.js';
import './Cards.css';
import "animate.css/animate.min.css";
import ScrollAnimation from 'react-animate-on-scroll';
import img from './images/pcb_1.png';
import img1 from './images/buzzer.png';
import img2 from './images/heat_1.png';
import img3 from './images/gliders.png';
import img4 from './images/parachute.png';


function Cards() {
  return(
    <div className='cards'>
      
      <div className='cards__containers'>
        <div className='cards__wrapper'>
          <ul className='cards__itemss'>
          <ScrollAnimation animateIn='flipInY'
  animateOut='flipOutY'>
            <CardItem
              src={img4}
              text='PARACHUTE'
              label='LANDING'
              path='/explore'
            />
            <CardItem
              src={img2}
              text='HEAT SHIELD'
              label='PROTECTION PURPOSE'
              path='/explore'
            />
            </ScrollAnimation>
          </ul>

          <ul className='cards__items'>
          <ScrollAnimation animateIn='flipInY'
  animateOut='flipOutY'>
            <CardItem
              src={img}
              text='PCB DESIGN'
              label='SUBSYSTEM'
              path='/explore'
            />
            <CardItem
              src={img3}
              text='GLIDERS'
              label='LANDING'
              path='/explore'
            />
            </ScrollAnimation>
            </ul>
            <ul className='cards__items'>
            <ScrollAnimation animateIn='flipInY'
  animateOut='flipOutY'> 
            <CardItem
              src={img1}
              text='BUZZER'
              label='TO IDENTIFY LANDING POSITION'
              path='/explore'
            />
            </ScrollAnimation>
            </ul>
       
          
        </div>
      </div>
  
    </div>
  );
}

export default Cards;