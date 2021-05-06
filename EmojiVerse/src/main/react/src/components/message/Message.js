import React from "react";

export class Message extends React.Component{
    constructor() {
        super();
        console.log(this.props)
    }

    render() {
        let messageToRender;
        if(this.props.content !== undefined){
            messageToRender =
                <div className={`message`}>
                    <div className='username'>
                        <h2>
                            { this.props.username }
                        </h2>
                    </div>
                    <div className='message-body'>
                        { this.props.content }
                    </div>
                    <div className='message-time'>
                        { this.props.timestamp}
                    </div>
                </div>
        }else {
            messageToRender = <p>Let's start Chatting!</p>
        }


        return (
            <div>
                {messageToRender}
            </div>
        );
    }
}