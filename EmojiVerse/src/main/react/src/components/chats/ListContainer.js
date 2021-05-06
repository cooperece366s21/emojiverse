import React, {useState} from "react";
import api from "../../services/api";
import {Button, Form} from "semantic-ui-react";

export class ListContainer extends React.Component{
    state = {
        chatNames : this.props.chatnames
    }

    render() {
        return(
            <div>{this.state.chatNames.map(name=>
                <Form.Field>
                    <Button onClick = {async () => directToChat(name)}>{name.replace("$","")}</Button>
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

function directToChat(name){
    api.getMessages(name,name.split(" participants: ")[0])
}