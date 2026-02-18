import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Resultados() {
  const resultado = JSON.parse(sessionStorage.getItem('ultimoResultado') || 'null');
  const [historial, setHistorial] = useState([]);
  const username = localStorage.getItem('username');
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8080/api/resultados/usuario/${username}`, {
      headers: { Authorization: `Bearer ${token}` }
    }).then(res => setHistorial(res.data))
    .catch(() => {});
  }, [username, token]);

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <button style={styles.backBtn} onClick={() => navigate('/')}>Volver</button>
        <h1>Resultados</h1>
      </div>

      {resultado && (
        <div style={styles.ultimoCard}>
          <h2>Ultimo Test</h2>
          <div style={styles.porcentaje}>{resultado.porcentaje}%</div>
          <div style={styles.stats}>
            <div style={styles.stat}>
              <span style={{color: '#28a745', fontSize: '24px', fontWeight: 'bold'}}>{resultado.correctas}</span>
              <span>Correctas</span>
            </div>
            <div style={styles.stat}>
              <span style={{color: '#dc3545', fontSize: '24px', fontWeight: 'bold'}}>{resultado.incorrectas}</span>
              <span>Incorrectas</span>
            </div>
            <div style={styles.stat}>
              <span style={{fontSize: '24px', fontWeight: 'bold'}}>{resultado.total}</span>
              <span>Total</span>
            </div>
          </div>
          <button style={styles.btnNuevo} onClick={() => navigate('/test/configurar')}>
            Nuevo Test
          </button>
        </div>
      )}

      <div style={styles.historialCard}>
        <h2>Historial de {username}</h2>
        {historial.length === 0 ? (
          <p style={{color: '#666', textAlign: 'center', padding: '20px'}}>
            No hay tests realizados aun
          </p>
        ) : (
          <table style={styles.table}>
            <thead>
              <tr style={styles.thead}>
                <th style={styles.th}>Fecha</th>
                <th style={styles.th}>Tipo</th>
                <th style={styles.th}>Correctas</th>
                <th style={styles.th}>Total</th>
                <th style={styles.th}>Porcentaje</th>
              </tr>
            </thead>
            <tbody>
              {historial.map(r => (
                <tr key={r.id} style={styles.tr}>
                  <td style={styles.td}>{new Date(r.fecha).toLocaleDateString()}</td>
                  <td style={styles.td}>
                    <span style={{background: '#17a2b8', color: 'white', padding: '3px 8px', borderRadius: '4px', fontSize: '12px'}}>
                      {r.tipoPreguntas}
                    </span>
                  </td>
                  <td style={styles.td}>{r.correctas}</td>
                  <td style={styles.td}>{r.totalPreguntas}</td>
                  <td style={styles.td}>
                    <span style={{
                      background: r.porcentaje >= 50 ? '#28a745' : '#dc3545',
                      color: 'white', padding: '3px 8px', borderRadius: '4px', fontSize: '12px'
                    }}>
                      {r.porcentaje?.toFixed(1)}%
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

const styles = {
  container: { padding: '20px', maxWidth: '900px', margin: '0 auto' },
  header: { display: 'flex', alignItems: 'center', gap: '20px', marginBottom: '20px' },
  backBtn: { padding: '8px 15px', background: '#6c757d', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' },
  ultimoCard: { background: 'white', padding: '30px', borderRadius: '10px', boxShadow: '0 2px 10px rgba(0,0,0,0.1)', marginBottom: '20px', textAlign: 'center' },
  porcentaje: { fontSize: '80px', fontWeight: 'bold', color: '#007bff', margin: '10px 0' },
  stats: { display: 'flex', justifyContent: 'center', gap: '40px', margin: '20px 0' },
  stat: { display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '5px' },
  btnNuevo: { padding: '12px 30px', background: '#007bff', color: 'white', border: 'none', borderRadius: '5px', fontSize: '16px', cursor: 'pointer' },
  historialCard: { background: 'white', padding: '30px', borderRadius: '10px', boxShadow: '0 2px 10px rgba(0,0,0,0.1)' },
  table: { width: '100%', borderCollapse: 'collapse' },
  thead: { background: '#343a40', color: 'white' },
  th: { padding: '12px 15px', textAlign: 'left' },
  tr: { borderBottom: '1px solid #eee' },
  td: { padding: '12px 15px' }
};

export default Resultados;