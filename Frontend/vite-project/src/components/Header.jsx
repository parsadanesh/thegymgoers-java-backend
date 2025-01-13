import { Link, useNavigate } from 'react-router-dom';

import React, { useState } from 'react';

const Header = (props) => {
    const navigate = useNavigate();
    const [isOpen, setIsOpen] = useState(false);

    const toggle = () => setIsOpen(!isOpen);

    const handleLogout = () => {
        props.setLoggedIn(false);
        navigate("login");
    }

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-dark">
            <a className="navbar-brand fw-bold text-success ms-3" href="#">The GymGoers</a>
            <button className="navbar-toggler bg-success me-3" type="button" onClick={toggle} aria-controls="navbarNav" aria-expanded={isOpen} aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className={`${isOpen ? 'show' : ''} collapse navbar-collapse`} id="navbarNav" data-testid="navbarNav">
                {!props.loggedIn &&
                    <ul className="navbar-nav">
                        <li className="nav-item active ">
                            <Link className="nav-link fw-bold text-success" to="/login">Login<span className="sr-only"></span></Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link fw-bold text-success" to="/sign-up">Sign Up</Link>
                        </li>
                    </ul>                   
                }
                {props.loggedIn &&
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link fw-bold text-success" to="/log">Log workout</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link fw-bold text-success" to="/viewWorkouts">View workout</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link fw-bold text-success" to="/gymGroups">GymGroups</Link>
                        </li>
                        <li className="nav-item" onClick={handleLogout}>
                            <span className="nav-link fw-bold text-success" style={{ cursor: 'pointer' }}>Log Out</span>
                        </li>
                        
                    </ul>
                }
            </div>
        </nav>
    );
}

export default Header;