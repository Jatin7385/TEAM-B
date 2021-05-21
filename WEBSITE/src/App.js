import './App.css'; 
import React from 'react';
import Navbar from './components/Navbar';
import Home from './components/pages/Home';
import Team from './components/pages/Team';
import Mission from './components/pages/Misson';
import Explore from './components/pages/Explore';
import {BrowserRouter as Router,Switch,Route} from 'react-router-dom';
import Model from './components/pages/Model';
function App() {
  return (
    <>
    <Router>
     
    <Navbar/>
    <Switch>
    <Route exact path='/'  component={Home} />
    <Route path='/teammates' component={Team}/>
    <Route path='/mission' component={Mission}/>
    <Route path='/explore' component={Explore}/>
    <Route path='/model' component={Model}/>
    
    </Switch>
    <br/><br/><br/>
     
    </Router>
    
</>

  );
}

export default App;
