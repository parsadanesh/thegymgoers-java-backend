import { useEffect, useState } from "react";
import UserDetailsForm from "../components/UserDetailsForm"
import Footer from "../components/Footer";
import Header from "../components/Header";
import RegisterUserForm from "../components/RegisterUserForm";


const RegisterPage = (props) => {

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        if (username!== "" && email !== "" && password !== "") {
            props.setNewUser({
                username: username,
                email: email,
                password: password
            })
            setUsername("")
            setEmail("");
            setPassword("");
        }
    }, [username, email, password])


    return (
        <div className="container">
            <h1>Register</h1>
            <br />
            <RegisterUserForm setUsername={setUsername} setEmail={setEmail} setPassword={setPassword} />
            {props.registrationMessage && <div className="alert alert-success" role="alert">{props.registrationMessage}</div>}
        </div>
    )

}

export default RegisterPage