import { useEffect, useState } from "react";
import UserDetailsForm from "../components/UserDetailsForm";

const LoginPage = (props) => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        if (username !== "" || password !== "") {
            props.setUser({
                username: username,
                password: password
            })
            setUsername("");
            setPassword("");
        }
    }, [username, password])


    return (
        <div className="container">
            <h1>Login</h1>
            <br/>
            <UserDetailsForm setUsername={setUsername} setPassword={setPassword} />
            {props.registrationMessage && <div className="alert alert-success" role="alert">{props.registrationMessage}</div>}
        </div>
    )
}

export default LoginPage