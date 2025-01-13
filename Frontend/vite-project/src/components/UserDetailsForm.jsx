import { useRef } from "react";

const UserDetailsForm = (props) => {
    const username = useRef();
    const pass = useRef();

    const handleSubmit = (e) => {
        e.preventDefault();
        props.setUsername(username.current.value);
        props.setPassword(pass.current.value);
    }

  
    return (
        <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '20px' }}>
            <label htmlFor="username" style={{ marginRight: '10px' }}>Email: </label>
            <input
              data-testid="username"
              type="username"
              name="username"
              id="username"
              placeholder="username"
              ref={username}
              required
            />
          </div>
          <div style={{ marginBottom: '20px' }}>
            <label htmlFor="password" style={{ marginRight: '10px' }}>Password: </label>
            <input
              data-testid="password"
              type="password"
              name="password"
              id="password"
              placeholder="Password"
              ref={pass}
              required
            />
          </div>
          <div>
            <input type="submit" value="Submit" />
          </div>
        </form>
    )
}

export default UserDetailsForm