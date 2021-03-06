import React, { Component } from 'react';

class DropDown extends Component {
    constructor(props) {
        super();
        this.state = {
            allItems: []
        };
    
    }

    //handleChange(event) {
    //    this.setState({value: event.target.value});
    //}

    sendData = (event) => {
        console.log(event.target.value);
        const elements = this.props.elements;

        let idx=elements.findIndex((e) => e===event.target.value);
        this.props.parentCallback(event.target.value,this.props,idx);
    }

    render() {
        const elements = this.props.elements;

        const items = [];

        elements.map((val,i)=>{
            items.push(<option key={i}>{val}</option>)
        });

        return (
            <div className="DropDown">
                <p>{this.props.description}</p>
                <select className="DropDownSelect" onChange={this.sendData}>
                    <option value="" selected disabled hidden>Choose one</option>
                    {items}
                </select>
            </div>
        )
    }
}

export default DropDown;