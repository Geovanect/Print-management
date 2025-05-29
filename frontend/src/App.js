import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [documents, setDocuments] = useState([]);
  const [newDocument, setNewDocument] = useState({ name: '', priority: false });

  useEffect(() => {
    fetchDocuments();
  }, []);

  const fetchDocuments = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/documents');
      const data = await response.json();
      setDocuments(data);
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

  return (
    <div className="App">
      <header className="App-header">
        <h1>Sistema de Gerenciamento de Impressão</h1>
      </header>
      
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
                <td>{doc.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default App; 