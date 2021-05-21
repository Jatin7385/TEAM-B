import React from 'react';
import ScrollAnimation from 'react-animate-on-scroll';
import '../../App.css';
import HeroSection from '../HeroSection';
import Text from '../Text';
import Cards from '../Cards';
import Footer from '../Footer';

function Home() {
  return (
    <>
      <HeroSection />
     <Text/>
     <br/><br/>
     <ScrollAnimation animateIn='bounceInRight'
  animateOut='bounceOutLeft'>
     <h2 style={{textAlign:"center",fontFamily:"cursive",fontSize:40 ,fontWeight:800, color:"white"}}>Check out every component of our CANSAT along with it's features</h2>
     </ScrollAnimation>
      <Cards/>
      <Footer/>
     
    </>
  );
}

export default Home;