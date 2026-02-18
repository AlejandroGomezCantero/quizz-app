import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function RealizarTest() {
  const preguntas = JSON.parse(sessionStorage.getItem('preguntas') || '[]');
  const tipoTest = sessionStorage.getItem('tipoTest');
  const [indice, setIndice] = useState(0);
  const [respuestas, setRespuestas] = useState([]);
  const [seleccion, setSeleccion] = useState(null);
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const username = localStorage.getItem('username');

  if (!preguntas.length) {
    navigate('/test/configurar');
    return null;
  }

  const pregunta = preguntas[indice];

  const handleSiguiente = async () => {
    const nuevasRespuestas = [...respuestas, seleccion];
    setSeleccion(null);

    if (indice + 1 >= preguntas.length) {
      let correctas = 0;
      preguntas.forEach((p, i) => {
        const r = nuevasRespuestas[i];
        if (tipoTest === 'VF' && String(p.respuestaCorrecta) === String(r)) correctas++;
        else if (tipoTest === 'UNICA' && p.opcionCorrecta === r) correctas++;
        else if (tipoTest === 'MULTIPLE' &&
          JSON.stringify([...(p.respuestasCorrectas || [])].sort()) ===
          JSON.stringify([...(r || [])].sort())) correctas++;
      });

      await axios.post('http://localhost:8080/api/resultados', {
        username,
        tipoPreguntas: tipoTest,
        totalPreguntas: preguntas.length,
        correctas,
        categoriaNombre: 'General'
      }, { headers: { Authorization: `Bearer ${token}` } });

      sessionStorage.setItem('ultimoResultado', JSON.stringify({
        total: preguntas.length,
        correctas,
        incorrectas: preguntas.length - correctas,
        porcentaje: ((correctas / preguntas.length) * 100).toFixed(1)
      }));

      navigate('/test/resultados');
    } else {
      setIndice(indice + 1);
      setRespuestas(nuevasRespuestas);
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.progreso}>
        <span>Pregunta {indice + 1} de {preguntas.length}</span>
        <span style={styles.badge}>{tipoTest}</span>
      </div>

      <div style={styles.barraContainer}>
        <div style={{...styles.barra, width: `${((indice + 1) / preguntas.length) * 100}%`}}></div>
      </div>

      <div style={styles.card}>
        <h3 style={styles.enunciado}>{pregunta.enunciado}</h3>

        {tipoTest === 'VF' && (
          <div style={styles.opciones}>
            {['true', 'false'].map(op => (
              <button key={op} style={{
                ...styles.opcion,
                background: seleccion === op ? (op === 'true' ? '#28a745' : '#dc3545') : '#f8f9fa',
                color: seleccion === op ? 'white' : 'black'
              }} onClick={() => setSeleccion(op)}>
                {op === 'true' ? 'Verdadero' : 'Falso'}
              </button>
            ))}
          </div>
        )}

        {tipoTest === 'UNICA' && (
          <div style={styles.opciones}>
            {['A', 'B', 'C'].map(op => (
              <button key={op} style={{
                ...styles.opcion,
                background: seleccion === op ? '#007bff' : '#f8f9fa',
                color: seleccion === op ? 'white' : 'black'
              }} onClick={() => setSeleccion(op)}>
                {op}) {pregunta[`opcion${op}`]}
              </button>
            ))}
          </div>
        )}

        {tipoTest === 'MULTIPLE' && (
          <div style={styles.opciones}>
            {['A', 'B', 'C', 'D'].map(op => {
              const sels = seleccion || [];
              const marcada = sels.includes(op);
              return (
                <button key={op} style={{
                  ...styles.opcion,
                  background: marcada ? '#ffc107' : '#f8f9fa'
                }} onClick={() => {
                  if (marcada) setSeleccion(sels.filter(s => s !== op));
                  else setSeleccion([...sels, op]);
                }}>
                  {op}) {pregunta[`opcion${op}`]}
                </button>
              );
            })}
            <small style={{color: '#666'}}>Puedes seleccionar varias opciones</small>
          </div>
        )}

        <button style={{
          ...styles.siguiente,
          opacity: seleccion === null || seleccion?.length === 0 ? 0.5 : 1
        }}
          disabled={seleccion === null || seleccion?.length === 0}
          onClick={handleSiguiente}>
          {indice + 1 >= preguntas.length ? 'Finalizar' : 'Siguiente'}
        </button>
      </div>
    </div>
  );
}

const styles = {
  container: { padding: '20px', maxWidth: '700px', margin: '0 auto' },
  progreso: { display: 'flex', justifyContent: 'space-between', marginBottom: '10px' },
  badge: { background: '#007bff', color: 'white', padding: '4px 10px', borderRadius: '4px' },
  barraContainer: { background: '#e9ecef', borderRadius: '5px', height: '10px', marginBottom: '20px' },
  barra: { background: '#007bff', height: '10px', borderRadius: '5px', transition: 'width 0.3s' },
  card: { background: 'white', padding: '30px', borderRadius: '10px', boxShadow: '0 2px 10px rgba(0,0,0,0.1)' },
  enunciado: { marginBottom: '25px', fontSize: '20px', lineHeight: '1.5' },
  opciones: { display: 'flex', flexDirection: 'column', gap: '10px', marginBottom: '20px' },
  opcion: { padding: '15px', border: '2px solid #dee2e6', borderRadius: '8px', cursor: 'pointer', fontSize: '16px', textAlign: 'left', transition: 'all 0.2s' },
  siguiente: { width: '100%', padding: '15px', background: '#343a40', color: 'white', border: 'none', borderRadius: '8px', fontSize: '18px', cursor: 'pointer' }
};

export default RealizarTest;