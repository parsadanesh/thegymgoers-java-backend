import { useRef } from "react";

const UserDetailsForm = (props) => {
    const email = useRef();
    const pass = useRef();

    const handleSubmit = (e) => {
        e.preventDefault();
        props.setEmail(email.current.value);
        props.setPassword(pass.current.value);
    }

  
    return (
        <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '20px' }}>
            <label htmlFor="email" style={{ marginRight: '10px' }}>Email: </label>
            <input
              data-testid="email"
              type="email"
              name="email"
              id="email"
              placeholder="you@domain.com"
              ref={email}
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