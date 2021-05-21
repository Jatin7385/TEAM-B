import React from 'react';
import './Footer.css';
import { Link } from 'react-router-dom';
import team from './images/teamsammard-removebg-preview.png';

function Footer() {
  return (
    <div className='footer-container'>
      <img src={team} alt="team sammard" style={{width:"10%", border:"0px"}}></img>
      <section className='footer-subscription'>
        <p className='footer-subscription-heading'>
         Join us in our mission to promote rocketery science among the youth
        </p>
      </section>
      <div class='footer-links'>
        <div className='footer-link-wrapper'>
          <div class='footer-link-items'>
            <h2>About Us</h2>
            <Link to='/sign-up'>How we work</Link>
            <Link to='/'>Competitions</Link>
            <Link to='/'>Scope</Link>
            <Link to='/'>Contributors</Link>
            <Link to='/'>Terms of Service</Link>
          </div>
          <div class='footer-link-items'>
            <h2>Contact Us</h2>
            <Link to='/'>Contact</Link>
            <Link to='/'>Support</Link>
            <Link to='/'>Sponsorships</Link>
          </div>
        </div>
        <div className='footer-link-wrapper'>
          <div class='footer-link-items'>
            <h2>Videos</h2>
            <Link to='/'>Submit Video</Link>
            <Link to='/'>Helping Staff</Link>
            <Link to='/'>Team</Link>
    
          </div>
          <div class='footer-link-items'>
            <h2>Social Media</h2>
            <Link to='/'>Instagram: team_sammard</Link>
            <Link to='/'>Facebook: teamsammard</Link>
            <Link to='/'>Youtube:  team sammard</Link>
            <Link to='/'>Twitter: team_sammard</Link>
          </div>
        </div>
      </div>
      <section class='social-media'>
        <div class='social-media-wrap'>
          <div class='footer-logo'>
            <Link to='/' className='social-logo'>
              TEAM SAMMARD
              <i class='fab fa-typo3' />
            </Link>
          </div>
          <small class='website-rights'>TEAM SAMMARD © 2020</small>
          <div class='social-icons'>
            <Link
              class='social-icon-link facebook'
              to='/'
              target='_blank'
              aria-label='Facebook'
            >
              <i class='fab fa-facebook-f' />
            </Link>
            <Link
              class='social-icon-link instagram'
              to='/'
              target='_blank'
              aria-label='Instagram'
            >
              <i class='fab fa-instagram' />
            </Link>
            <Link
              class='social-icon-link youtube'
              to='/'
              target='_blank'
              aria-label='Youtube'
            >
              <i class='fab fa-youtube' />
            </Link>
            <Link
              class='social-icon-link twitter'
              to='/'
              target='_blank'
              aria-label='Twitter'
            >
              <i class='fab fa-twitter' />
            </Link>
            <Link
              class='social-icon-link twitter'
              to='/'
              target='_blank'
              aria-label='LinkedIn'
            >
              <i class='fab fa-linkedin' />
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
}

export default Footer;