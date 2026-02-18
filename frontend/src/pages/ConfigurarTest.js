import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function ConfigurarTest() {
  const [categorias, setCategorias] = useState([]);
  const [tipo, setTipo] = useState('VF');
  const [cantidad, setCantidad] = useState(5);
  const [categoriaId, setCategoriaId] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  useEffect(() => {
    axios.get('http://localhost:8080/api/categorias', {
      headers: { Authorization: `Bearer ${token}` }
    }).then(res => setCategorias(res.data))
    .catch(() => {});
  }, [token]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      let url = '';
      if (tipo === 'VF') url = 'http://localhost:8080/api/vf/random';
      else if (tipo === 'UNICA') url = 'http://localhost:8080/api/seleccion-unica/random';
      else url = 'http://localhost:8080/api/seleccion-multiple/random';

      const res = await axios.get(url, {
        headers: { Authorization: `Bearer ${token}` },
        params: { cantidad, categoriaId: categoriaId || null }
      });

      sessionStorage.setItem('preguntas', JSON.stringify(res.data));
      sessionStorage.setItem('tipoTest', tipo);
      navigate('/test/realizar');
    } catch (err) {
      setError('Error al cargar preguntas. Hay preguntas en la BD?');
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <button style={styles.backBtn} onClick={() => navigate('/')}>Volver</button>
        <h1>Configurar Test</h1>
      </div>

      {error && <p style={styles.error}>{error}</p>}

      <div style={styles.card}>
        <form onSubmit={handleSubmit}>
          <div style={styles.field}>
            <label style={styles.label}>Tipo de Preguntas</label>
            <select style={styles.input} value={tipo}
              onChange={e => setTipo(e.target.value)}>
              <option value="VF">Verdadero/Falso</option>
              <option value="UNICA">Seleccion Unica</option>
              <option value="MULTIPLE">Seleccion Multiple</option>
            </select>
          </div>

          <div style={styles.field}>
            <label style={styles.label}>Cantidad de preguntas (1-20)</label>
            <input style={styles.input} type="number"
              min="1" max="20"
              value={cantidad}
              onChange={e => setCantidad(e.target.value)} />
          </div>

          <div style={styles.field}>
            <label style={styles.label}>Categoria (opcional)</label>
            <select style={styles.input} value={categoriaId}
              onChange={e => setCategoriaId(e.target.value)}>
              <option value="">Todas las categorias</option>
              {categorias.map(cat => (
                <option key={cat.id} value={cat.id}>{cat.nombre}</option>
              ))}
            </select>
          </div>

          <button style={styles.button} type="submit">
            Empezar Test
          </button>
        </form>
      </div>
    </div>
  );
}

const styles = {
  container: { padding: '20px', maxWidth: '600px', margin: '0 auto' },
  header: { display: 'flex', alignItems: 'center', gap: '20px', marginBottom: '20px' },
  backBtn: { padding: '8px 15px', background: '#6c757d', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer' },
  card: { background: 'white', padding: '30px', borderRadius: '10px', boxShadow: '0 2px 10px rgba(0,0,0,0.1)' },
  field: { marginBottom: '20px', display: 'flex', flexDirection: 'column', gap: '8px' },
  label: { fontWeight: 'bold', fontSize: '16px' },
  input: { padding: '10px', borderRadius: '5px', border: '1px solid #ddd', fontSize: '16px' },
  button: { width: '100%', padding: '12px', background: '#28a745', color: 'white', border: 'none', borderRadius: '5px', fontSize: '18px', cursor: 'pointer' },
  error: { color: 'red', background: '#ffe0e0', padding: '10px', borderRadius: '5px' }
};

export default ConfigurarTest;