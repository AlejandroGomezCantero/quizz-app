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
        <h1>Quiz App</h1>
        <div>
          <span style={styles.username}>Usuario: {username}</span>
          <button style={styles.logoutBtn} onClick={logout}>Cerrar Sesion</button>
        </div>
      </div>

      <div style={styles.cards}>
        <div style={styles.card}>
          <h3>Gestion de Usuarios</h3>
          <p>Ver y gestionar usuarios del sistema</p>
          <button style={styles.btn} onClick={() => navigate('/usuarios')}>
            Ir a Usuarios
          </button>
        </div>

        <div style={styles.card}>
          <h3>Realizar Test</h3>
          <p>Pon a prueba tus conocimientos</p>
          <button style={{...styles.btn, background: '#28a745'}}
            onClick={() => navigate('/test/configurar')}>
            Empezar Test
          </button>
        </div>

        <div style={styles.card}>
          <h3>Mis Resultados</h3>
          <p>Ver historial de tests realizados</p>
          <button style={{...styles.btn, background: '#17a2b8'}}
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
  header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px', padding: '20px', background: '#343a40', color: 'white', borderRadius: '10px' },
  username: { marginRight: '15px', fontSize: '16px' },
  logoutBtn: { padding: '8px 15px', background: '#dc3545', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' },
  cards: { display: 'flex', gap: '20px', flexWrap: 'wrap' },
  card: { flex: 1, minWidth: '250px', background: 'white', padding: '30px', borderRadius: '10px', boxShadow: '0 2px 10px rgba(0,0,0,0.1)', textAlign: 'center' },
  btn: { padding: '10px 20px', background: '#007bff', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', fontSize: '16px', marginTop: '10px' }
};

export default Home;