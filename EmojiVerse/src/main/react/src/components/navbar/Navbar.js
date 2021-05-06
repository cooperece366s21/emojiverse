import React from 'react'
import styled from "styled-components";
import { useSpring, animated, config } from "react-spring";
import Brand from "./Brand";
import BurgerMenu from "./BurgerMenu";
import CollapseMenu from "./CollapseMenu";
import { Form, Input, Button } from 'semantic-ui-react';
import api from '../../services/api'
const Navbar = (props) => {
  const barAnimation = useSpring({
    from: { transform: 'translate3d(0, -10rem, 0)' },
    transform: 'translate3d(0, 0, 0)',
  });
  const linkAnimation = useSpring({
    from: { transform: 'translate3d(0, 30px, 0)', opacity: 0 },
    to: { transform: 'translate3d(0, 0, 0)', opacity: 1 },
    delay: 800,
    config: config.wobbly,
  });
  
  const username = localStorage.getItem("username")
  
  return (
    <>
      <NavBar style={barAnimation}>
        <FlexContainer>
		
          <Brand />
		  
          <NavLinks style={linkAnimation}>
           <div class="ui yellow button">
    <i class="bitcoin icon"></i>EmojiCoins
  </div><a class="ui basic left pointing label">
    1,048
  </a>
			<Button className = "ui inverted button"><a href = "/chatList">ChatList</a></Button>
            <Button className="ui inverted button" onClick = {async () => api.getEmojiStore(username)}><a href="/store">Store</a></Button>
            <Button className = "ui inverted button"><a href="/profile">Profile</a></Button>
			<Button className="ui inverted button"><a href="/">Log-Out</a></Button>
			
			
			
  
  
          </NavLinks>
          <BurgerWrapper>
            <BurgerMenu
              navbarState={props.navbarState} 
              handleNavbar={props.handleNavbar}
            />
          </BurgerWrapper>
        </FlexContainer>
		
      </NavBar>
      <CollapseMenu 
        navbarState={props.navbarState} 
        handleNavbar={props.handleNavbar}
      />
   </>
  )
}
export default Navbar
const NavBar = styled(animated.nav)`
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
  background: white;
  z-index: 1;
  font-size: 1.4rem;
`;
const FlexContainer = styled.div`
  max-width: 120rem;
  display: flex;
  margin: auto;
  padding: 0 2rem;;
  word-spacing: 200px;
  height: 6rem;
`;
const NavLinks = styled(animated.ul)`
  justify-self: end;
  list-style-type: none;
  margin: auto 0;
  & a {
    color: #dfe6e9;
    text-transform: uppercase;
    font-weight: 600;
    border-bottom: 1px solid transparent;
    margin: 0 1.5rem;
    transition: all 300ms linear 0s;
    text-decoration: none;
    cursor: pointer;
    &:hover {
      color: CornflowerBlue;
      border-bottom: 1px solid CornflowerBlue;;
    }
    @media (max-width: 768px) {
      display: none;
    }
  }
`;
const BurgerWrapper = styled.div`
  margin: auto 0;
  @media (min-width: 769px) {
    display: none;
  }
`;
