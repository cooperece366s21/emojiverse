import React from "react";
import {Message} from "./Message";

export class Messages extends React.Component{
    render() {
        const messages = this.props.messages.map((message, i) => {
            return (
                <Message
                    key={i}
                    username={message.username}
                    content={message.content}
                    timestamp={message.timestamp} />
            );
        });
        return (
            <div className='messages' id='messageList'>
                { messages }
            </div>
        );
    }

}