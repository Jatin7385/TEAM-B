import React, { Component } from "react";

import ReactCardCarousel from "react-card-carousel";

class MyCarousel extends Component {
  static get CONTAINER_STYLE() {
    return {
      position: "relative",
      height: "73vh",
      width: "100%",
      display: "flex",
      flex: 1,
      justifyContent: "center",
      alignItems: "middle",
      marginTop:"80px"
    };
  }

  static get CARD_STYLE() {
    return {
      height: "350px",
      width: "300px",
      paddingTop: "100px",
      textAlign: "center",
      background: "linear-gradient(to right, #fc5c7d, #6a82fb)",
      color: "black",
      fontFamily:"Fira Sans",
      fontSize: "31px",
      textTransform: "uppercase",
      borderRadius: "10px",
      boxSizing: "border-box"
    };
  }

  render() {
    return (
      <div style={MyCarousel.CONTAINER_STYLE}>
        <ReactCardCarousel autoplay={true} autoplay_speed={2500}>
          <div style={MyCarousel.CARD_STYLE}>Mohan<br/><br/>
          <p>(Mechanical Dept.)</p>
          </div>
          <div style={MyCarousel.CARD_STYLE}>Harsh<br/><br/>
          <p>(Mechanical Dept).</p>
          </div>
          <div style={MyCarousel.CARD_STYLE}>Chiran<br/><br/>
          <p>(Mechanical Dept.)</p>
          </div>
          <div style={MyCarousel.CARD_STYLE}>Pradyumna<br/><br/>
          <p>(Technical Dept.)</p>
          </div>
          <div style={MyCarousel.CARD_STYLE}>Eshan<br/><br/>
          <p>(Electronics Dept.)</p>
          </div>
        </ReactCardCarousel>
      </div>
    );
  }
}

export default MyCarousel;