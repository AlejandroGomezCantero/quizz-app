import { useNavigate } from 'react-router-dom';

function Home() {
  const username = localStorage.getItem('username');
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    navigate('/login');
  };

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h1>🎯 Quiz App</h1>
        <div>
          <span style={styles.username}>👤 {username}</span>
          <button style={styles.logoutBtn} onClick={logout}>Cerrar Sesión</button>
        </div>
      </div>

      <div style={styles.cards}>

        <div style={styles.card}>
          <div style={styles.icon}>👥</div>
          <h3>Gestión de Usuarios</h3>
          <p style={styles.desc}>Ver y gestionar usuarios del sistema</p>
          <button style={styles.btn} onClick={() => navigate('/usuarios')}>
            Ir a Usuarios
          </button>
        </div>

        <div style={styles.card}>
          <div style={styles.icon}>🧠</div>
          <h3>Realizar Test</h3>
          <p style={styles.desc}>10 preguntas al azar de todos los tipos</p>
          <button style={{ ...styles.btn, background: '#28a745' }}
            onClick={() => navigate('/test/realizar')}>
            Empezar Test
          </button>
        </div>

        <div style={styles.card}>
          <div style={styles.icon}>📊</div>
          <h3>Mis Resultados</h3>
          <p style={styles.desc}>Ver historial de tests realizados</p>
          <button style={{ ...styles.btn, background: '#17a2b8' }}
            onClick={() => navigate('/test/resultados')}>
            Ver Resultados
          </button>
        </div>

      </div>
    </div>
  );
}

const styles = {
  container: { padding: '20px', maxWidth: '1000px', margin: '0 auto' },
  header: {
    display: 'flex', justifyContent: 'space-between', alignItems: 'center',
    marginBottom: 30, padding: 20, background: '#212529', color: 'white', borderRadius: 12
  },
  username: { marginRight: 15, fontSize: 16 },
  logoutBtn: { padding: '8px 15px', background: '#dc3545', color: 'white', border: 'none', borderRadius: 6, cursor: 'pointer' },
  cards: { display: 'flex', gap: 20, flexWrap: 'wrap' },
  card: {
    flex: 1, minWidth: 250, background: 'white', padding: 30,
    borderRadius: 12, boxShadow: '0 4px 15px rgba(0,0,0,0.08)', textAlign: 'center'
  },
  icon: { fontSize: 52, marginBottom: 12 },
  desc: { color: '#6c757d', marginBottom: 16, fontSize: 15 },
  btn: {
    padding: '10px 24px', background: '#007bff', color: 'white',
    border: 'none', borderRadius: 6, cursor: 'pointer', fontSize: 16
  },
};

export default Home;