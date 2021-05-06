import React, {useState} from "react";
import api from "../../services/api";
import {Button, Form} from "semantic-ui-react";
import Chat from "./Chat"

export class ChatList extends React.Component{
    state = {
        chatNames : this.props.chatnames,
        chat : null
    }

    render() {
        return(
			<>
            <div>{this.state.chatNames.map(name=>
                <Form.Field>
                    <Button onClick = {async () => directToChat(name.split(" participants: ")[0], this)}>{name.replace("$","")}</Button>
                    <Button onClick={async () => deleteChat(name, this)}>Remove</Button>
                </Form.Field>
            )}</div>
            <Form id="chat_view">
		{this.state.chat}
            </Form>
            </>
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

function directToChat(name, component){
	console.log(name)
	//component.setState({ chat: "hello there" })
	component.setState({ chat: < Chat chatName={name}/> })
    //api.getMessages(name,name.split(" participants: ")[0])
}
