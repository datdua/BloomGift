import React, { useState, useEffect } from 'react';
import { Navbar, NavDropdown, Dropdown } from 'react-bootstrap';
import { PersonCircle, Gear, BoxArrowRight } from 'react-bootstrap-icons';
import { useHistory } from 'react-router-dom';
import './HeaderSidebar.css';

const Header = ({ username, avatarUrl }) => {
    const [showDropdown, setShowDropdown] = useState(false);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const history = useHistory();

    useEffect(() => {

        const token = localStorage.getItem("token");

        setIsLoggedIn(!!token);
    }, []);

    const handleLogout = () => {
        localStorage.removeItem("token");
        setIsLoggedIn(false);
        history.push("/login-register");
    };

    return (
        <Navbar bg="white" expand="lg" className="py-2 px-4 shadow-sm" style={{ height: '64px', width: '100%', maxWidth: '100%' }}>
            <div className="d-flex justify-content-between align-items-center w-100">
                <Navbar.Brand href="#home" className="d-flex align-items-center">
                    <img
                        src="https://firebasestorage.googleapis.com/v0/b/bloom-gift-67f83.appspot.com/o/element-layout%2Flogo%2Ffavicon_48x48.png?alt=media&token=c8dc92cf-204b-43a6-8d21-6bc9677f027a"
                        width="30"
                        height="30"
                        className="d-inline-block align-top"
                        alt="BloomGift logo"
                    />
                    <span className="ml-2">BloomGift</span>
                </Navbar.Brand>
                <div className="d-flex align-items-center">
                    <span className="mr-2">{username}</span>
                    <NavDropdown
                        title={
                            <div className="d-inline-block">
                                {avatarUrl ? (
                                    <img
                                        src={avatarUrl}
                                        width="32"
                                        height="32"
                                        className="rounded-circle"
                                        alt={username}
                                    />
                                ) : (
                                    <PersonCircle size={32} />
                                )}
                            </div>
                        }
                        id="basic-nav-dropdown"
                        show={showDropdown}
                        onMouseEnter={() => setShowDropdown(true)}
                        onMouseLeave={() => setShowDropdown(false)}
                        align="end"
                    >
                        <Dropdown.Item href="#profile" className="d-flex align-items-center">
                            <PersonCircle className="mr-2" /> Hồ Sơ Shop
                        </Dropdown.Item>
                        <Dropdown.Item href="#settings" className="d-flex align-items-center">
                            <Gear className="mr-2" /> Thiết Lập Shop
                        </Dropdown.Item>
                        <Dropdown.Divider />
                        <Dropdown.Item href="#logout" className="d-flex align-items-center text-danger" onClick={handleLogout}>
                            <BoxArrowRight className="mr-2" /> Đăng xuất
                        </Dropdown.Item>
                    </NavDropdown>
                </div>
            </div>
        </Navbar>
    );
};

export default Header;
