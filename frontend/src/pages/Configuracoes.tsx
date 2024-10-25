import React, { useEffect, useState } from 'react';
import { Form, Button, Container, Row, Col, Card, ProgressBar } from 'react-bootstrap';
import axios from 'axios';

interface Tecnologia {
  nome: string;
  comandoInstalacao: string[];
  cmd: string;
  exposedPorts: number[];
}

const Configuracoes = () => {
  const [tecnologias, setTecnologias] = useState<Tecnologia[]>([]);
  const [selectedTech, setSelectedTech] = useState<string[]>([]);
  const [nomeProjeto, setNomeProjeto] = useState('');
  const [nomeContainer, setNomeContainer] = useState('');
  const [loading, setLoading] = useState(false);  
  const [message, setMessage] = useState('');  

  useEffect(() => {
    axios.get('http://localhost:8080/api/tecnologias')
      .then(response => {
        setTecnologias(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar tecnologias', error);
      });
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');  // Limpar mensagem ao submeter

    const configuracao = {
      nomeProjeto,
      nomeContainer,
      tecnologiasSelecionadas: selectedTech,
    };

    try {
      const response = await axios.post('http://localhost:8080/api/configuracoes', configuracao);
      setMessage(response.data);  // Exibe a resposta após o sucesso
    } catch (error) {
      console.error('Erro ao enviar configuração', error);
      setMessage('Erro ao criar o container.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-center">
        <Col md={12}>
          {loading && (
            <ProgressBar animated now={100} variant="info" className="mb-3" />
          )}

          <Card className="p-4 shadow-lg border-0 rounded bg-light text-dark mb-5">
            <Card.Body>
              <h3>Configuração de Container</h3>

              {message && !loading && (
                <div className="alert alert-success mt-2">
                  {message}
                </div>
              )}

              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="nomeProjeto">
                  <Form.Label>Nome do Projeto</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Digite o nome do projeto"
                    value={nomeProjeto}
                    onChange={(e) => setNomeProjeto(e.target.value)}
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="nomeContainer">
                  <Form.Label>Nome do Container</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Digite o nome do container"
                    value={nomeContainer}
                    onChange={(e) => setNomeContainer(e.target.value)}
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="tecnologias">
                  <Form.Label>Tecnologias</Form.Label>
                  {tecnologias.map((tech) => (
                    <Form.Check
                      type="checkbox"
                      key={tech.nome}
                      label={tech.nome}
                      value={tech.nome}
                      onChange={(e) => {
                        const value = e.target.value;
                        setSelectedTech((prev) =>
                          prev.includes(value)
                            ? prev.filter((item) => item !== value)
                            : [...prev, value]
                        );
                      }}
                    />
                  ))}
                </Form.Group>

                <Button variant="primary" type="submit" disabled={loading}>
                  Criar Configuração
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Configuracoes;
