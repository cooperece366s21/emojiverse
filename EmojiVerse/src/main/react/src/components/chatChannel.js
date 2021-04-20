import React from "react";
import {Messages} from "./Messages";


let dummyMessage = [
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


export class chatChannel extends React.Component{

    render() {
        return(
            <div className="container">
                <h3>React Chat App</h3>
                <Messages messages={dummyMessage}/>
            </div>);
    }

}
