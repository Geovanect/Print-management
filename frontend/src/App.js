import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [documents, setDocuments] = useState([]);
  const [newDocument, setNewDocument] = useState({ name: '', priority: false });
  const [printMessage, setPrintMessage] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [hasCompletedDocuments, setHasCompletedDocuments] = useState(false);

  useEffect(() => {
    fetchDocuments();
  }, [currentPage]);

  // Auto-refresh a cada 2 segundos
  useEffect(() => {
    const interval = setInterval(() => {
      fetchDocuments();
    }, 2000);
    return () => clearInterval(interval);
  }, [currentPage]);

  const fetchDocuments = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/documents?page=${currentPage}&size=7`);
      const data = await response.json();
      setDocuments(data.content);
      setTotalPages(data.totalPages);
      setTotalElements(data.totalElements);
      
      // Verifica se existem documentos completados
      const hasCompleted = data.content.some(doc => doc.status === "COMPLETED");
      setHasCompletedDocuments(hasCompleted);
    } catch (error) {
      console.error('Error fetching documents:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/api/documents', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newDocument),
      });
      if (response.ok) {
        setNewDocument({ name: '', priority: false });
        fetchDocuments();
      }
    } catch (error) {
      console.error('Error adding document:', error);
    }
  };

  const handlePrint = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/documents/print', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      
      if (response.status === 404) {
        throw new Error('Não há documentos na fila para imprimir');
      }
      
      if (!response.ok) {
        throw new Error('Erro ao imprimir documento');
      }

      const printedDoc = await response.json();
      if (printedDoc.status === 'IMPRIMINDO') {
        setPrintMessage(`O arquivo "${printedDoc.name}" está sendo impresso...`);
        // Após 5 segundos, buscar novamente e mostrar mensagem de sucesso
        setTimeout(async () => {
          await fetchDocuments();
          setPrintMessage(`O arquivo "${printedDoc.name}" foi impresso com sucesso!`);
          setTimeout(() => setPrintMessage(''), 3000);
        }, 5000);
      } else if (printedDoc.status === 'COMPLETED') {
        setPrintMessage(`O arquivo "${printedDoc.name}" foi impresso com sucesso!`);
        setTimeout(() => setPrintMessage(''), 3000);
      }
      fetchDocuments();
    } catch (error) {
      console.error('Erro ao imprimir documento:', error);
      setPrintMessage(error.message || 'Erro ao imprimir documento');
      setTimeout(() => setPrintMessage(''), 3000);
    }
  };

  const handleCleanCompleted = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/documents/clean-completed', {
        method: 'DELETE'
      });

      if (response.ok) {
        setPrintMessage('Documentos impressos foram removidos com sucesso!');
        fetchDocuments();
        setTimeout(() => setPrintMessage(''), 3000);
      } else {
        throw new Error('Erro ao limpar documentos impressos');
      }
    } catch (error) {
      console.error('Erro ao limpar documentos:', error);
      setPrintMessage(error.message || 'Erro ao limpar documentos impressos');
      setTimeout(() => setPrintMessage(''), 3000);
    }
  };

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Sistema de Gerenciamento de Impressão</h1>
      </header>
      
      {printMessage && (
        <div className="print-message">
          {printMessage}
        </div>
      )}

      <div className="add-document-form">
        <h2>Adicionar Novo Documento</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Nome do Documento"
            value={newDocument.name}
            onChange={(e) => setNewDocument({ ...newDocument, name: e.target.value })}
            required
          />
          <label className="priority-label">
            <input
              type="checkbox"
              checked={newDocument.priority}
              onChange={(e) => setNewDocument({ ...newDocument, priority: e.target.checked })}
            />
            Documento Prioritário
          </label>
          <button type="submit">Adicionar Documento</button>
        </form>
      </div>

      <div className="documents-list">
        <h2>Fila de Impressão</h2>
        <div className="queue-info">
          <p>Documentos prioritários serão impressos primeiro, mantendo a ordem de chegada</p>
          <div className="button-group">
            <button 
              className="print-button"
              onClick={handlePrint}
              disabled={totalElements === 0}
            >
              Imprimir Documento
            </button>
          </div>
        </div>
        <table>
          <thead>
            <tr>
              <th>Nome</th>
              <th>Prioridade</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {documents.map((doc) => (
              <tr key={doc.id} className={doc.priority ? 'priority-row' : ''}>
                <td>
                  {doc.name}
                  {doc.priority && <span className="priority-badge">Prioritário</span>}
                </td>
                <td>{doc.priority ? 'Sim' : 'Não'}</td>
                <td>{
                  doc.status === 'PENDING' ? 'Pendente' :
                  doc.status === 'IMPRIMINDO' ? 'Imprimindo' :
                  doc.status === 'COMPLETED' ? 'Concluído' :
                  doc.status
                }</td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Botão Limpar Fila abaixo da tabela */}
        {hasCompletedDocuments && (
          <div className="clean-fila-wrapper">
            <button 
              className="red-button"
              onClick={handleCleanCompleted}
            >
              Limpar Fila
            </button>
          </div>
        )}

        {totalPages > 1 && (
          <div className="pagination">
            <button 
              onClick={() => handlePageChange(currentPage - 1)}
              disabled={currentPage === 0}
              className="pagination-button"
            >
              Anterior
            </button>
            <span className="page-info">
              Página {currentPage + 1} de {totalPages}
            </span>
            <button 
              onClick={() => handlePageChange(currentPage + 1)}
              disabled={currentPage === totalPages - 1}
              className="pagination-button"
            >
              Próxima
            </button>
          </div>
        )}
      </div>
    </div>
  );
}

export default App; 