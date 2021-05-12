/*https://github.com/react-z/react-emojipicker/blob/master/src/Picker.js*/
/*change send button to async post and get*/

import React, {Component, useState, useEffect} from 'react'
import styled from 'styled-components'
import emojione from 'emojione'
import PropTypes from 'prop-types'
import { Form, Input, Button } from 'semantic-ui-react';
import api from '../../services/api';
import {Message} from '../message/Message';






const username = localStorage.getItem("username")
const chat = localStorage.getItem("chat")








/* list of emoji's sourced from http://getemoji.com */
const PEOPLE_EMOJIS = ['😀', '😃', '😄', '😁', '😆', '😅', '😂', '😇', '🤣', '☺️', '😊', '🙂', '🙃', '😉', '😌', '😍', '😘', '😗', '😙', '😚', '😋', '😜', '😝', '😛', '🤑', '🤗', '🤓', '😎', '🤡', '🤠', '😏', '😒', '😞', '😔', '😟', '😕', '🙁', '☹️', '😣', '😖', '😫', '😩', '😤', '😠', '😡', '😶', '😐', '😑', '😯', '😦', '😧', '😮', '😲', '😵', '😳', '😱', '😨', '😰', '😢', '😥', '🤤', '😭', '😓', '😪', '😴', '🙄', '🤔', '🤥', '😬', '🤐', '🤢', '🤧', '😷', '🤒', '🤕', '😈', '👿', '👹', '👺', '💩', '👻', '💀', '☠️', '👽', '👾', '🤖', '🎃', '😺', '😸', '😹', '😻', '😼', '😽', '🙀', '😿', '😾', '👐', '🙌', '👏', '🙏', '🤝', '👍', '👎', '👊', '✊', '🤛', '🤜', '🤞', '✌️', '🤘', '👌', '👈', '👉', '👆', '👇', '☝️', '✋', '🤚', '🖐', '🖖', '👋', '🤙', '💪', '🖕', '✍️', '🤳', '💅', '🖖', '💄', '💋', '👄', '👅', '👂', '👃', '👣', '👁', '👀', '👗', '👠', '👞', '👟', '👒', '🎩', '🎓', '👑', '⛑', '🎒', '👝', '👛', '👜', '💼', '👓', '🕶', '☂️']
const ANIMALS_NATURE_EMOJIS = ['🐶', '🐱', '🐭', '🐹', '🐰', '🦊', '🐻', '🐼', '🐨', '🐯', '🦁', '🐮', '🐷', '🐽', '🐸', '🐵', '🙊', '🙉', '🙊', '🐒', '🐔', '🐧', '🐦', '🐤', '🐣', '🐥', '🦆', '🦅', '🦉', '🦇', '🐺', '🐗', '🐴', '🦄', '🐝', '🐛', '🦋', '🐌', '🐚', '🐞', '🐜', '🕷', '🕸', '🐢', '🐍', '🦎', '🦂', '🦀', '🦑', '🐙', '🦐', '🐠', '🐟', '🐡', '🐬', '🦈', '🐳', '🐋', '🐊', '🐆', '🐅', '🐃', '🐂', '🐄', '🦌', '🐪', '🐫', '🐘', '🦏', '🦍', '🐎', '🐖', '🐐', '🐏', '🐑', '🐕', '🐩', '🐈', '🐓', '🦃', '🕊', '🐇', '🐁', '🐀', '🐿', '🐾', '🐉', '🐲', '🌵', '🎄', '🌲', '🌳', '🌴', '🌱', '🌿', '☘️', '🍀', '🎍', '🎋', '🍃', '🍂', '🍁', '🍄', '🌾', '💐', '🌷', '🌹', '🥀', '🌻', '🌼', '🌸', '🌺', '🌎', '🌍', '🌏', '🌕', '🌖', '🌔', '🌚', '🌝', '🌞', '🌛', '🌜', '🌙', '💫', '⭐️', '🌟', '✨', '⚡️', '🔥', '💥', '☄️', '☀️', '🌤', '⛅️', '🌥', '🌦', '🌈', '☁️', '🌧', '⛈', '🌩', '🌨', '☃️', '⛄️', '❄️', '🌬', '💨', '🌪', '🌫', '🌊', '💧', '💦', '☔️']
const FOOD_SPORTS_EMOJIS = ['🍏', '🍎', '🍐', '🍊', '🍋', '🍌', '🍉', '🍇', '🍓', '🍈', '🍒', '🍑', '🍍', '🥝', '🥑', '🍅', '🍆', '🥒', '🥕', '🌽', '🌶', '🥔', '🍠', '🌰', '🥜', '🍯', '🥐', '🍞', '🥖', '🧀', '🥚', '🍳', '🥓', '🥞', '🍤', '🍗', '🍖', '🍕', '🌭', '🍔', '🍟', '🥙', '🌮', '🌯', '🥗', '🥘', '🍝', '🍜', '🍲', '🍥', '🍣', '🍱', '🍛', '🍚', '🍙', '🍘', '🍢', '🍡', '🍧', '🍨', '🍦', '🍰', '🎂', '🍮', '🍭', '🍬', '🍫', '🍿', '🍩', '🍪', '🥛', '🍼', '☕️', '🍵', '🍶', '🍺', '🍻', '🥂', '🍷', '🥃', '🍸', '🍹', '🍾', '🥄', '🍴', '🍽', '⚽️', '🏀', '🏈', '⚾️', '🎾', '🏐', '🏉', '🎱', '🏓', '🏸', '🥅', '🏒', '🏑', '🏏', '⛳️', '🏹', '🎣', '🥊', '🥋', '⛸', '🎿', '⛷', '🏂', '🏋', '🤺', '⛹️', '🏌', '🏄', '🏊', '🤽', '🚣', '🏇', '🚴', '🚵', '🎬', '🎤', '🎧', '🎼', '🎹', '🥁', '🎷', '🎺', '🎸', '🎻', '🎲', '🎯', '🎳', '🎮', '🏳', '🏴', '🏁', '🚩', '🎽', '🥇', '🥈', '🥉', '🏆']
const TRAVEL_PLACES_EMOJIS = ['🚗', '🚕', '🚙', '🚌', '🚎', '🏎', '🚓', '🚑', '🚒', '🚐', '🚚', '🚛', '🚜', '🛴', '🚲', '🛵', '🏍', '🚨', '🚔', '🚍', '🚘', '🚖', '🚡', '🚠', '🚟', '🚃', '🚋', '🚞', '🚝', '🚄', '🚅', '🚈', '🚂', '🚆', '🚇', '🚊', '🚉', '🚁', '🛩', '✈️', '🛫', '🛬', '🚀', '🛰', '💺', '🛶', '⛵️', '🛥', '🚤', '🛳', '⛴', '🚢', '⚓️', '🚧', '⛽️', '🚏', '🚦', '🚥', '🗺', '🗿', '🗽', '⛲️', '🗼', '🏰', '🏯', '🏟', '🎡', '🎢', '🎠', '⛱', '🏖', '🏝', '⛰', '🏔', '🗻', '🌋', '🏜', '🏕', '⛺️', '🛤', '🛣', '🏗', '🏭', '🏠', '🏡', '🏘', '🏚', '🏢', '🏬', '🏣', '🏤', '🏥', '🏦', '🏨', '🏪', '🏫', '🏩', '💒', '🏛', '⛪️', '🕌', '🕍', '🕋', '⛩', '🗾', '🎑', '🏞', '🌅', '🌄', '🌠', '🎇', '🎆', '🌇', '🌆', '🏙', '🌃', '🌌', '🌉', '🌁', '🎭', '🎨']
const OBJECTS_EMOJIS = ['🆓', '📗', '📕', '⌚️', '📱', '📲', '💻', '⌨️', '🖥', '🖨', '🖱', '🖲', '🕹', '🗜', '💽', '💾', '💿', '📀', '📼', '📷', '📸', '📹', '🎥', '📽', '🎞', '📞', '☎️', '📟', '📠', '📺', '📻', '🎙', '🎚', '🎛', '⏱', '⏲', '⏰', '🕰', '⌛️', '⏳', '📡', '🔋', '🔌', '💡', '🔦', '🕯', '🗑', '🛢', '💸', '💵', '💴', '💶', '💷', '💰', '💳', '💎', '⚖️', '🔧', '🔨', '⚒', '⛏', '⚙️', '💣', '🔪', '🗡', '⚔️', '🛡', '🚬', '⚰️', '⚱️', '🏺', '🔮', '📿', '💈', '⚗️', '🔭', '🔬', '🕳', '💊', '💉', '🌡', '🚽', '🚰', '🚿', '🛁', '🛀', '🛎', '🔑', '🗝', '🚪', '🛋', '🛏', '🖼', '🛍', '🛒', '🎁', '🎈', '🎏', '🎀', '🎊', '🎉', '🎎', '🏮', '🎐', '✉️', '📪', '📮', '📯', '📜', '📃', '📄', '📑', '📊', '📈', '📉', '🗒', '🗓', '📆', '📅', '📇', '🗃', '🗳', '🗄', '📋', '🗞', '📰', '📘', '📚', '📖', '🔖', '🔗', '📎', '📐', '📏', '📍', '📌', '🖊', '🖌', '🖍', '📝', '✏️', '🔍', '🔓']
const SYMBOLS_FLAGS_EMOJIS = ['❤️', '💛', '💚', '💙', '💜', '🖤', '💔', '❣️', '💕', '💞', '💓', '💗', '💖', '💘', '💝', '💟', '☮️', '✝️', '☪️', '🕉', '☸️', '✡️', '🔯', '🕎', '☯️', '☦️', '🛐', '⛎', '♈️', '♉️', '♊️', '♋️', '♌️', '♍️', '♎️', '♏️', '♐️', '♑️', '♒️', '♓️', '🆔', '⚛️', '🉑', '☢️', '☣️', '📴', '📳', '🈶', '🈚', '🈸', '🈺', '🈷', '✴️', '🆚', '💮', '🉐', '㊙️', '㊗️', '🈴', '🈵', '🈹', '🈲', '❌', '⭕️', '🛑', '⛔️', '📛', '🚫', '💯', '💢', '♨️', '🚷', '🚯', '🚳', '🚱', '🔞', '📵', '🚭', '❕', '❔', '‼️', '⁉️', '🔅', '🔆', '〽️', '⚠️', '🚸', '🔱', '⚜️', '🔰', '♻️', '✅', '🈯', '💹', '❇️', '✳️', '❎', '🌐', '💠', 'Ⓜ️', '🌀', '💤', '🚺', '🚼', '🎵', '🎶', '➕', '➖', '➗', '✖️', '💲', '💱', '™️', '©️', '®️', '〰️', '➰', '➿', '🔚', '🔙', '🔛', '🔝', '✔️', '☑️', '🔈', '🔇', '🔉', '🔊', '🔔', '🔕', '📣', '📢', '🗨', '💬', '💭', '🗯', '♠️', '♣️', '♥️', '♦️', '🃏', '🎴', '🀄']

export default class ChatClass extends Component {
  constructor (props) {
    super(props)
    this.state = {
      emojis: localStorage.getItem("USER_PEOPLE_EMOJIS").split(','),
      emojiCategory: 'PEOPLE_EMOJIS',
	  message: "",
	  username: username,
      message_info:localStorage.getItem("message_info").split(',')
	  
    }
  }

  static get propTypes () {
    return {
     
      visible: PropTypes.bool,
      modal: PropTypes.bool
    }
  }

  static get defaultProps () {
    return {
      visible: true,
      modal: false
    }
  }
  
  componentDidMount()
  {
	/*
	  PEOPLE_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "PEOPLE_EMOJIS", -1,username)})
	  
	  ANIMALS_NATURE_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "ANIMALS_NATURE_EMOJIS", -1,username)})
	  
	  FOOD_SPORTS_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "FOOD_SPORTS_EMOJIS", -1,username)})
	  
	  TRAVEL_PLACES_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "TRAVEL_PLACES_EMOJIS", -1, username)})
	  
	  OBJECTS_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "OBJECTS_EMOJIS", -1,username)})
	  
	  SYMBOLS_FLAGS_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "SYMBOLS_FLAGS_EMOJIS", -1,username)})
	*/
    updateChat(this,document);

    setInterval(updateChat, 500,this,document);
  }
  
  

  toggleEmojis (emoji) {
    switch (emoji) {
      case 'PEOPLE_EMOJIS':
        this.setState({emojis: localStorage.getItem("USER_PEOPLE_EMOJIS").split(','), emojiCategory: 'PEOPLE_EMOJIS'})
        break;
      case 'ANIMALS_NATURE_EMOJIS':
        this.setState({emojis:  localStorage.getItem("USER_ANIMALS_NATURE_EMOJIS").split(','), emojiCategory: 'ANIMALS_NATURE_EMOJIS'})
        break;
      case 'FOOD_SPORTS_EMOJIS':
        this.setState({emojis: localStorage.getItem("USER_FOOD_SPORTS_EMOJIS").split(','), emojiCategory: 'FOOD_SPORTS_EMOJIS'})
        break;
      case 'TRAVEL_PLACES_EMOJIS':
        this.setState({emojis:  localStorage.getItem("USER_TRAVEL_PLACES_EMOJIS").split(','), emojiCategory: 'TRAVEL_PLACES_EMOJIS'})
        break;
      case 'OBJECTS_EMOJIS':
        this.setState({emojis: localStorage.getItem("USER_OBJECTS_EMOJIS").split(','), emojiCategory: 'OBJECTS_EMOJIS'})
        break;
      case 'SYMBOLS_FLAGS_EMOJIS':
        this.setState({emojis: localStorage.getItem("USER_SYMBOLS_FLAGS_EMOJIS").split(','), emojiCategory: 'SYMBOLS_FLAGS_EMOJIS'})
        break;
      default:
        this.setState({emojis:  localStorage.getItem("USER_PEOPLE_EMOJIS").split(','), emojiCategory: 'PEOPLE_EMOJIS'})
    }
  }


  onEmojiSelect (e) {
    if(e.target.alt === undefined) { return }

    let emoji = {
      image: e.target,
      unicode: e.target.alt,
      shortname: e.target.title
    }
    
  }

  renderTabs () {
    const {emojiCategory} = this.state
    return (
      <Tabs>
        <Title
          selected={emojiCategory === 'PEOPLE_EMOJIS'}
          onClick={() => {this.toggleEmojis('PEOPLE_EMOJIS')}}
          dangerouslySetInnerHTML={{__html: emojione.unicodeToImage('😀')}} />

        <Title
          selected={emojiCategory === 'ANIMALS_NATURE_EMOJIS'}
          onClick={() => {this.toggleEmojis('ANIMALS_NATURE_EMOJIS')}}
          dangerouslySetInnerHTML={{__html: emojione.unicodeToImage('🦊')}} />
        <Title
          selected={emojiCategory === 'FOOD_SPORTS_EMOJIS'}
          onClick={() => {this.toggleEmojis('FOOD_SPORTS_EMOJIS')}}
          dangerouslySetInnerHTML={{__html: emojione.unicodeToImage('🍏')}} />
        <Title
          selected={emojiCategory === 'TRAVEL_PLACES_EMOJIS'}
          onClick={() => {this.toggleEmojis('TRAVEL_PLACES_EMOJIS')}}
          dangerouslySetInnerHTML={{__html: emojione.unicodeToImage('🚗')}} />
        <Title
          selected={emojiCategory === 'OBJECTS_EMOJIS'}
          onClick={() => {this.toggleEmojis('OBJECTS_EMOJIS')}}
          dangerouslySetInnerHTML={{__html: emojione.unicodeToImage('💎')}} />
        <Title
          selected={emojiCategory === 'SYMBOLS_FLAGS_EMOJIS'}
          onClick={() => {this.toggleEmojis('SYMBOLS_FLAGS_EMOJIS')}}
          dangerouslySetInnerHTML={{__html: emojione.unicodeToImage('❤️')}} />
      </Tabs>
    )
  }

  render() {
    const {emojis} = this.state
    const {visible, modal} = this.props
	const {username} = this.state
	
	
    return (
	
	<Form>
	
	
	<Form.Field className = "white-box">
	
	<Button className = "ui inverted button" style ={{color : 'blue'}}onClick = {async() => backToChatList(username)}>BACK TO CHANNEL LIST</Button>
	<h1>{chat}</h1>

	


        <div className="chatWindow" id="messageWindow">

            <div>{this.state.message_info == null ? this.state.message_info : this.state.message_info.map(info=>
                    <Message username={info.split(":")[0].split("(")[0]} content={info.split(") :")[1]}
                             timestamp={info.split(") :")[0].split("(")[1]}/>
                    // <h3 className = "message">{info}</h3>
            )}</div>
        </div>

	
      <Wrapper>
        <EmojiPickerWrapper visible={visible} modal={modal}>
          {this.renderTabs()}
          <EmojiWrapper>
            {
              emojis.map((emoji, index) => (
                <Emoji
                  className='ld-emoji'
                  key={index}

                  role='presentation'
                  onClick={()=>{this.setState({message : this.state.message + emojione.unicodeToImage(emoji)})}}
                  dangerouslySetInnerHTML={{__html: emojione.unicodeToImage(emoji)}} />

              ))
            }
          </EmojiWrapper>
        </EmojiPickerWrapper>
      </Wrapper>
	  <Input
        placeholder="Enter Message"
		value = {this.state.message}
        />
		<Button onClick = {
		    async ()=>{
		        api.sendMessage(chat.split(" participants: ")[0], username, this.state.message,updateChat, this, document);
		        this.setState({
                  message: ""
                });
		    }
        }>Send</Button>


	 </Form.Field>
	 
	 </Form>
    )
	
  }
}

function updateChat(component, doc){
  const tempMsg = component.state.message_info;
  component.setState({
    message_info:localStorage.getItem("message_info").split(',')
  })

  // if there is a change for the current message, scroll to the bottom of the window to show
  if(component.state.message_info !== tempMsg){
    var objDiv = doc.getElementById("messageWindow");
    objDiv.scrollTop = objDiv.scrollHeight;
  }
}


const Wrapper = styled.div`
  position: relative;
`

const EmojiPickerWrapper = styled.div`
  position: ${props => props.modal ? 'absolute' : 'static'};
  opacity: ${props => props.visible ? 1 : 0};
  transition: opacity 300ms linear;
  margin-top: 1rem;
  border: 1px solid #F1F1F1;
  border-radius: 2px;
  background: white;
  box-shadow: 3px 3px 5px #BFBDBD;
  width: 500px;
  height: 150px;
  overflow-y: scroll;
  z-index: 100;
`

const EmojiPicker = styled.div`
  cursor: pointer;
  border: 1px solid #eee;
  padding: 0.4rem 0.8rem;
  margin: 0;
  border-radius: 2px;
  &:hover {
    background: rgba(0, 0, 0, 0.1);
  }
`

export const EmojiWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  padding: 0.8rem;
  padding-right: 0;
  border-radius: 2px;
  align-items: baseline;
  float: left;
  width: 75%;
`

const Emoji = styled.span`
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0.21rem;
  img {
    height: 16px !important;
    width: 16px !important;
  }
`

const Tabs = styled.div`
  flex-direction: column;
  float: left;
  width: 15%;
  height: 100%;
  border-right: 1px solid rgba(0, 0, 0, 0.1);
`

const Title = styled.p`
  padding-left: 0.35rem;
  padding: 0.8rem
  margin: 0;
  cursor: pointer;
  img {
	opacity: ${props => props.selected ? '1' : '0.5'};
    height: 24px !important;
    width: 24px !important;
  }
`
export const Chat = () =>{
	return <ChatClass />
}

function backToChatList(username)
{
	api.loadEmojiCoins(username)
	window.location.replace("http://localhost:3000/chatList")
}