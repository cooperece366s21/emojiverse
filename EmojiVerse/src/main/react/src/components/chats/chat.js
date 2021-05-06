/*https://github.com/react-z/react-emojipicker/blob/master/src/Picker.js*/
/*change send button to async post and get*/

import React, { Component } from 'react'
import styled from 'styled-components'
import emojione from 'emojione'
import PropTypes from 'prop-types'
import { Form, Input, Button } from 'semantic-ui-react';
import api from '../../services/api';
import {Message} from '../message/message';






const username = localStorage.getItem("username")
const chat = localStorage.getItem("chat")
var message_info = localStorage.getItem("message_info")
if(message_info!= null)
{
	message_info = localStorage.getItem("message_info").split(',')
}









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
      emojis: PEOPLE_EMOJIS,
      emojiCategory: 'PEOPLE_EMOJIS',
	  message: "",
	  username: username
	  
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
	  uncomment to populate. one-time code
	  
	  PEOPLE_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "PEOPLE_EMOJIS", 0)})
	  
	  ANIMALS_NATURE_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "ANIMALS_NATURE_EMOJIS", Math.ceil(Math.random()*1000))})
	  
	  FOOD_SPORTS_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "FOOD_SPORTS_EMOJIS", Math.ceil(Math.random()*1500))})
	  
	  TRAVEL_PLACES_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "FOOD_SPORTS_EMOJIS", Math.ceil(Math.random()*2000))})
	  
	  OBJECTS_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "FOOD_SPORTS_EMOJIS", Math.ceil(Math.random()*2500))})
	  
	  SYMBOLS_FLAGS_EMOJIS.map(emoji=>
	  {api.populateEmojiStore(emoji, "SYMBOLS_FLAGS_EMOJIS", Math.ceil(Math.random()*3000))})
	  */
	  
	  
	
  }
  
  

  toggleEmojis (emoji) {
    switch (emoji) {
      case 'PEOPLE_EMOJIS':
        this.setState({emojis: PEOPLE_EMOJIS, emojiCategory: 'PEOPLE_EMOJIS'})
        break;
      case 'ANIMALS_NATURE_EMOJIS':
        this.setState({emojis: ANIMALS_NATURE_EMOJIS, emojiCategory: 'ANIMALS_NATURE_EMOJIS'})
        break;
      case 'FOOD_SPORTS_EMOJIS':
        this.setState({emojis: FOOD_SPORTS_EMOJIS, emojiCategory: 'FOOD_SPORTS_EMOJIS'})
        break;
      case 'TRAVEL_PLACES_EMOJIS':
        this.setState({emojis: TRAVEL_PLACES_EMOJIS, emojiCategory: 'TRAVEL_PLACES_EMOJIS'})
        break;
      case 'OBJECTS_EMOJIS':
        this.setState({emojis: OBJECTS_EMOJIS, emojiCategory: 'OBJECTS_EMOJIS'})
        break;
      case 'SYMBOLS_FLAGS_EMOJIS':
        this.setState({emojis: SYMBOLS_FLAGS_EMOJIS, emojiCategory: 'SYMBOLS_FLAGS_EMOJIS'})
        break;
      default:
        this.setState({emojis: PEOPLE_EMOJIS, emojiCategory: 'PEOPLE_EMOJIS'})
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
	
	<a href = "http://localhost:3000/chatList">BACK TO CHANNEL LIST</a>
	<h1>{chat}</h1>

	


        <div className="chatWindow">

            <div>{message_info == null ? message_info : message_info.map(info=>
                    <Message username={info.split(":")[0].split("(")[0]} content={info.split(") :")[1]} timestamp={info.split(") :")[0].split("(")[1]}/>
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
		<Button onClick = {async () => {

		  const chat_name = chat.split(" participants: ")[0]
		  const userName = username
		  const Message = this.state.message
          const response = await fetch("/addMessage", {
            method: "POST",
            headers: {
              "Content_Type": "application/json"
            },
            body:
              JSON.stringify({
			  chatName: chat_name, username : userName, message : Message})
		  })
			if (response.ok) {
            console.log("Response Worked! ");

			response.json().then(data=>{
				console.log(data)
				localStorage.setItem("message_info",data.message_info);
				window.location.replace("http://localhost:3000/chat")
			});

            }
			else
			{
				console.log("not found")

		}}}>Send</Button>


	 </Form.Field>
	 
	 </Form>
    )
	
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
