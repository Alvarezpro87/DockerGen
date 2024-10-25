import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';

const Footer: React.FC = () => {
  return (
    <footer className="bg-secondary text-light py-4 w-100">
      <Container>
        <Row>
          <Col md={6}>
            <p>&copy; 2024 DockerGen. Todos os direitos reservados.</p>
          </Col>
          <Col md={6} className="text-md-end">
            <a href="https://github.com/Alvarezpro87" className="text-light me-3">GitHub</a>
            <a href="https://www.linkedin.com/in/alexandre" className="text-light me-3">LinkedIn</a>
            <a href="https://my-portifolio-amber-three.vercel.app/my-projects" className="text-light">Portifolio</a>
          </Col>
        </Row>
      </Container>
    </footer>
  );
}

export default Footer;
