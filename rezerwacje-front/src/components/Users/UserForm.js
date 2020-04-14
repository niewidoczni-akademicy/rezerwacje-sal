import React, { Component } from 'react'
import FormUserDetails from './FormUserDetails'
import Confirm from './Confirm'



export class UserForm extends Component {
    state = {
        step: 1,
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        role: '',
        login: '',
        password: '',
        repeatPassword: ''
        
    }

    nextStep = () => {
        const { step } = this.state;
        this.setState({
            step: step + 1
        });
    }

    prevStep = () => {
        const { step } = this.state;
        this.setState({
            step: step - 1
        });
    }

    handleChange = input => e => {
        this.setState({[input]: e.target.value})
    }

    handleAdd = (firstName, lastName, email, phone, login, password) => {
        fetch("/api/system-user", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userUniqueId: login, firstName: firstName, lastName: lastName, emailAddress: email, phoneNumber: phone, 
        login: login,  password: password}),
          }).then(
            function (res) {
              if (res.ok) {
                alert("Użytkownik został dodany do bazy.");
              } else if (res.status === 400) {
                alert("Wystąpił błąd.");
              }
            },
            function (e) {
              alert("Wystąpił błąd.");
            }
          );
    }

    render() {
        const { step } = this.state
        const { firstName, lastName, email, phone, role, login, password, resetPassword } = this.state
        const values = { firstName, lastName, email, phone, role, login, password, resetPassword}

        switch (step) {
            case 1:
                return (
                    <FormUserDetails
                        nextStep={this.nextStep}
                        handleChange={this.handleChange}
                        values={values}
                    />
                )
            case 2:
                return(<Confirm
                        nextStep={this.nextStep}
                        prevStep={this.prevStep}
                        values={values}
                        />)
            case 3:
                this.handleAdd(firstName, lastName, email, phone, login, password)
                this.setState({step: 1, firstName: '', 
                    lastName: '', email: '', 
                    phone: '', role: '', 
                    login: '', password: '', 
                    repeatPassword: ''})
                return (<UserForm/>)
        
        }

        return (
            <div>
                
            </div>
        )
    }
}

export default UserForm
