import React, {useState} from "react";
import api from "../../services/api";
import {Button, Form} from "semantic-ui-react";

export class ChatList extends React.Component{
    state = {
        chatNames : this.props.chatnames,
		username : localStorage.getItem("username")
    }

    render() {
        return(
            <div>{this.state.chatNames.map(name=>
                <Form.Field>
                    <Button onClick = {async () => directToChat(name,this.state.username)}>{name.replace("$","")}</Button>
                    <Button onClick={async () => deleteChat(name, this)}>Remove</Button>
                </Form.Field>
            )}</div>
        )
    }


}

function deleteChat(name,component){
    let mchatName = component.state.chatNames;
    const tempChatName = name.split(" participants: ")[1].split(",")[1].replace("$","").replace("]", "").trim()
    api.removeChat(tempChatName, name.split(" participants: ")[0])

    component.setState({
        chatNames: mchatName.filter(chat => chat.toString() != name.toString())
    })
}

function directToChat(name,username){
    api.getMessages(name,name.split(" participants: ")[0])
	api.getUserEmojis(username)
}