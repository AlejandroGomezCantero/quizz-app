import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Usuarios() {
  const [usuarios, setUsuarios] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  useEffect(() => {
    axios.get('http://localhost:8080/api/usuarios', {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => setUsuarios(res.data))
    .catch(() => setError('Error al cargar usuarios'));
  }, [token]);

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <button style={styles.backBtn} onClick={() => navigate('/')}>Volver</button>
        <h1>Gestion de Usuarios</h1>
      </div>

      {error && <p style={styles.error}>{error}</p>}

      <table style={styles.table}>
        <thead>
          <tr style={styles.thead}>
            <th style={styles.th}>ID</th>
            <th style={styles.th}>Usuario</th>
            <th style={styles.th}>Nombre</th>
            <th style={styles.th}>Email</th>
            <th style={styles.th}>Rol</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map(u => (
            <tr key={u.id} style={styles.tr}>
              <td style={styles.td}>{u.id}</td>
              <td style={styles.td}>{u.username}</td>
              <td style={styles.td}>{u.nombreCompleto}</td>
              <td style={styles.td}>{u.email}</td>
              <td style={styles.td}>
                <span style={{
                  padding: '4px 8px',
                  borderRadius: '4px',
                  background: u.rol === 'ROLE_ADMIN' ? '#dc3545' : '#6c757d',
                  color: 'white',
                  fontSize: '12px'
                }}>
                  {u.rol === 'ROLE_ADMIN' ? 'ADMIN' : 'USER'}
                </span>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

const styles = {
  container: { padding: '20px', maxWidth: '1000px', margin: '0 auto' },
  header: { display: 'flex', alignItems: 'center', gap: '20px', marginBottom: '20px' },
  backBtn: { padding: '8px 15px', background: '#6c757d', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' },
  error: { color: 'red' },
  table: { width: '100%', borderCollapse: 'collapse', background: 'white', borderRadius: '10px', overflow: 'hidden', boxShadow: '0 2px 10px rgba(0,0,0,0.1)' },
  thead: { background: '#343a40', color: 'white' },
  th: { padding: '12px 15px', textAlign: 'left' },
  tr: { borderBottom: '1px solid #eee' },
  td: { padding: '12px 15px' }
};

export default Usuarios;