import React from "react";
import {Messages} from "./Messages";


const dummyMessage = [
    {
        username: 'Bonny',
        content: 'hi',
        timestamp: '0'
    },
    {
        username: 'Dan',
        content: 'hi',
        timestamp: '1'
    },
    {
        username: 'Nikita',
        content: 'hi',
        timestamp: '2'
    }
];


export class chatChannel extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    render() {
        return (
            <div className="container">
                <h3>EmojiVerse Chatting</h3>
                <Messages messages={dummyMessage}/>
                <form onSubmit={this.handleSubmit}>
                    <label>
                        <input type="text" name="name"  value={this.state.value} onChange={this.handleChange} />
                    </label>
                    <input type="submit" value="Send"/>
                </form>
            </div>);

    }

    addMessage(message){
        dummyMessage.push(message);
    }

    handleChange(event){
        this.setState({value: event.target.value});
    }

    handleSubmit(event) {
        let msg = {
            username: 'Me',
            content: this.state.value,
            timestamp: Date().toLocaleString()
        };

        this.addMessage(msg);
        console.log(dummyMessage);
        event.preventDefault();
    }
}
