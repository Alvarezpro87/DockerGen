import React from 'react';
import {Container, Row, Col, Button } from 'react-bootstrap';

const Home: React.FC = () => {
  return (
    <Container className="mt-5" style={{ width: '30vw' }}  >
      <Row className="text-center">
        <Col className="container mt-5  justify-content-center">
          <h1>Bem-vindo ao DockerGen</h1>
          <p>Automatize a criação de ambientes Docker personalizados com facilidade.</p>
          <Button variant="primary" href="/configuracoes">Criar Configuração</Button>
        </Col>
      </Row>
    </Container>
  );
}

export default Home;
