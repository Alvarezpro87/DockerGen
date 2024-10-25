import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import { LiaDocker } from "react-icons/lia";

const Header: React.FC = () => {
  return (
    <Navbar  variant="dark" expand="lg" className="w-100 bg-secondary ">
      <Container>
       
        <Navbar.Brand href="/" className=" align-items-center">
           
          <h2><LiaDocker size={50} className="me-2" />DockerGen</h2>
        </Navbar.Brand>

        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav  className="ms-auto ">
            <Nav.Link className='text-light' href="/">Home</Nav.Link>
            <Nav.Link className='text-light' href="/configuracoes">Configurações</Nav.Link>
            <Nav.Link className='text-light' href="/gerenciar">Gerenciar Container</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;
