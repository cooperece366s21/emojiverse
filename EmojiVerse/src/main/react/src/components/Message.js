import React from "react";

export class Message extends React.Component{
    render() {
        return (
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
        );
    }
}