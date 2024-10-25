import { useEffect, useState } from 'react';
import { Card, Button, Row, Col, Container } from 'react-bootstrap';
import { FaPlay, FaStop, FaTrash, FaDesktop } from 'react-icons/fa';
import api from '../service/api';

// Tipagem do container
interface Container {
  id: string;
  name: string;
  status: string;
  ports: Array<{ IP: string, PublicPort: number }>;
}

const GerenciarContainers = () => {
  const [containers, setContainers] = useState<Container[]>([]);

  useEffect(() => {
    // Chamada para listar os containers
    api.get('/configuracoes/listar')
      .then(response => {
        setContainers(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar containers:', error);
      });
  }, []);

  const handleStartContainer = (id: string) => {
    api.post(`/configuracoes/iniciar/${id}`)
      .then(() => {
        refreshContainers();
      })
      .catch(error => {
        console.error('Erro ao iniciar container:', error);
      });
  };

  const handleStopContainer = (id: string) => {
    api.post(`/configuracoes/parar/${id}`)
      .then(() => {
        refreshContainers();
      })
      .catch(error => {
        console.error('Erro ao parar container:', error);
      });
  };

  const handleRemoveContainer = (id: string) => {
    api.delete(`/configuracoes/remover/${id}`)
      .then(() => {
        refreshContainers();
      })
      .catch(error => {
        console.error('Erro ao remover container:', error);
      });
  };

  const refreshContainers = () => {
    api.get('/configuracoes/listar')
      .then(response => {
        setContainers(response.data);
      })
      .catch(error => {
        console.error('Erro ao buscar containers:', error);
      });
  };

  return (
    <div className="container mt-5"> 
    <Container className="mt-4  ">
      <h2 className="text-center mb-4">Gerenciamento de Containers</h2>
      <Row >
        {containers.map((container) => (
          <Col key={container.id} md={6} className="mb-4">
            <Card className="shadow-sm p-3 h-100 ">
              <Card.Body>
                <Card.Title className="text-center">{container.name || 'Sem Nome'}</Card.Title>
                <Card.Text><strong>ID:</strong> {container.id}</Card.Text>
                <Card.Text><strong>Status:</strong> {container.status}</Card.Text>
                
                  <div align="center">
                  <Button size="sm"  variant="success" className="me-2" onClick={() => handleStartContainer(container.id)}>
                    <FaPlay /> Start
                  </Button>
                  <Button size="sm"  variant="warning"className="me-2" onClick={() => handleStopContainer(container.id)}>
                    <FaStop /> Stop
                  </Button>
                  <Button size="sm" variant="danger" onClick={() => handleRemoveContainer(container.id)}>
                    <FaTrash /> Delete
                  </Button>
                  </div>

                {container.status === 'running' && container.ports.length > 0 && (
                  <Button
                    variant="primary"
                    href={`http://localhost:${container.ports[0].PublicPort}`}
                    target="_blank"
                    className="mt-3 w-100"
                    size="sm"
                  >
                    <FaDesktop /> Acessar Interface Web
                  </Button>
                )}
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
    </div>
  );
};

export default GerenciarContainers;
