import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function RealizarTest() {
  const [preguntas, setPreguntas]   = useState([]);
  const [indice, setIndice]         = useState(0);
  const [respuestas, setRespuestas] = useState([]);
  const [seleccion, setSeleccion]   = useState(null);
  const [cargando, setCargando]     = useState(true);
  const [error, setError]           = useState('');
  const [finalizado, setFinalizado] = useState(false);
  const [resultado, setResultado]   = useState(null);

  const navigate = useNavigate();
  const token    = localStorage.getItem('token');
  const username = localStorage.getItem('username');

  const cargarPreguntas = useCallback(async () => {
    try {
      setCargando(true);
      setError('');
      setFinalizado(false);
      setResultado(null);
      setIndice(0);
      setRespuestas([]);
      setSeleccion(null);

      const headers = { Authorization: `Bearer ${token}` };

      const [resVF, resUnica, resMultiple] = await Promise.all([
        axios.get('http://localhost:8080/api/vf/random?cantidad=4', { headers }),
        axios.get('http://localhost:8080/api/seleccion-unica/random?cantidad=3', { headers }),
        axios.get('http://localhost:8080/api/seleccion-multiple/random?cantidad=3', { headers }),
      ]);

      const vf       = (resVF.data       || []).map(p => ({ ...p, _tipo: 'VF'       }));
      const unica    = (resUnica.data    || []).map(p => ({ ...p, _tipo: 'UNICA'    }));
      const multiple = (resMultiple.data || []).map(p => ({ ...p, _tipo: 'MULTIPLE' }));

      const todas = [...vf, ...unica, ...multiple].sort(() => Math.random() - 0.5);

      if (todas.length === 0) {
        setError('No hay preguntas disponibles en la base de datos.');
        setCargando(false);
        return;
      }

      setPreguntas(todas);
      setCargando(false);
    } catch (err) {
      console.error('Error cargando preguntas:', err);
      setError('No se pudieron cargar las preguntas. Comprueba que el servidor está arrancado.');
      setCargando(false);
    }
  }, [token]);

  useEffect(() => {
    cargarPreguntas();
  }, [cargarPreguntas]);

  const handleSiguiente = async () => {
    const nuevasRespuestas = [...respuestas, seleccion];

    if (indice + 1 >= preguntas.length) {
      let correctas = 0;
      preguntas.forEach((p, i) => {
        const r = nuevasRespuestas[i];
        if (p._tipo === 'VF') {
          if (String(p.respuestaCorrecta) === String(r)) correctas++;
        } else if (p._tipo === 'UNICA') {
          if (p.opcionCorrecta === r) correctas++;
        } else if (p._tipo === 'MULTIPLE') {
          const correctasOrdenadas = [...(p.respuestasCorrectas || [])].sort().join(',');
          const respuestaOrdenada  = [...(r || [])].sort().join(',');
          if (correctasOrdenadas === respuestaOrdenada) correctas++;
        }
      });

      const total       = preguntas.length;
      const incorrectas = total - correctas;
      const porcentaje  = ((correctas / total) * 100).toFixed(1);
      const notaSobre10 = ((correctas / total) * 10).toFixed(1);

      try {
        await axios.post('http://localhost:8080/api/resultados', {
          username,
          tipoPreguntas: 'MIXTO',
          totalPreguntas: total,
          correctas,
          categoriaNombre: 'General'
        }, { headers: { Authorization: `Bearer ${token}` } });
      } catch (e) {
        console.error('Error guardando resultado:', e);
      }

      const res = { total, correctas, incorrectas, porcentaje, notaSobre10 };
      sessionStorage.setItem('ultimoResultado', JSON.stringify(res));
      setResultado(res);
      setFinalizado(true);

    } else {
      setRespuestas(nuevasRespuestas);
      setIndice(indice + 1);
      setSeleccion(null);
    }
  };

  if (cargando) return (
    <div style={styles.center}>
      <div style={styles.spinner}></div>
      <p style={{ marginTop: 20, color: '#666', fontSize: 18 }}>Cargando preguntas...</p>
    </div>
  );

  if (error) return (
    <div style={styles.center}>
      <div style={{ fontSize: 60, marginBottom: 16 }}>⚠️</div>
      <p style={{ color: '#dc3545', marginBottom: 20, fontSize: 16, textAlign: 'center' }}>{error}</p>
      <div style={{ display: 'flex', gap: 12 }}>
        <button style={styles.btnPrimario} onClick={cargarPreguntas}>🔄 Reintentar</button>
        <button style={styles.btnSecundario} onClick={() => navigate('/')}>🏠 Inicio</button>
      </div>
    </div>
  );

  if (finalizado && resultado) {
    const color = resultado.porcentaje >= 50 ? '#198754' : '#dc3545';
    const emoji = resultado.porcentaje >= 90 ? '🏆'
                : resultado.porcentaje >= 70 ? '🎉'
                : resultado.porcentaje >= 50 ? '👍'
                : '😅';

    return (
      <div style={styles.center}>
        <div style={styles.resultadoCard}>
          <div style={{ fontSize: 70, marginBottom: 10 }}>{emoji}</div>
          <h2 style={{ marginBottom: 6 }}>¡Test completado!</h2>
          <div style={{ ...styles.notaGrande, color }}>
            {resultado.notaSobre10}
            <span style={{ fontSize: 28, color: '#666' }}> / 10</span>
          </div>
          <p style={{ color: '#666', marginBottom: 24 }}>{resultado.porcentaje}% de aciertos</p>
          <div style={styles.statsRow}>
            <div style={styles.statBox}>
              <span style={{ fontSize: 28, fontWeight: 'bold', color: '#198754' }}>{resultado.correctas}</span>
              <span style={{ color: '#666', fontSize: 14 }}>Correctas</span>
            </div>
            <div style={styles.statBox}>
              <span style={{ fontSize: 28, fontWeight: 'bold', color: '#dc3545' }}>{resultado.incorrectas}</span>
              <span style={{ color: '#666', fontSize: 14 }}>Incorrectas</span>
            </div>
            <div style={styles.statBox}>
              <span style={{ fontSize: 28, fontWeight: 'bold', color: '#0d6efd' }}>{resultado.total}</span>
              <span style={{ color: '#666', fontSize: 14 }}>Total</span>
            </div>
          </div>
          <p style={{ color: '#198754', fontSize: 14, marginBottom: 24 }}>✅ Resultado guardado en tu historial</p>
          <div style={{ display: 'flex', gap: 12, justifyContent: 'center', flexWrap: 'wrap' }}>
            <button style={styles.btnPrimario} onClick={cargarPreguntas}>🔄 Nuevo Test</button>
            <button style={{ ...styles.btnPrimario, background: '#17a2b8' }} onClick={() => navigate('/test/resultados')}>📊 Ver Historial</button>
            <button style={styles.btnSecundario} onClick={() => navigate('/')}>🏠 Inicio</button>
          </div>
        </div>
      </div>
    );
  }

  const pregunta    = preguntas[indice];
  const progresoPct = ((indice + 1) / preguntas.length) * 100;
  const tipoBadge   = {
    VF:       { label: 'Verdadero/Falso',    bg: '#0dcaf0', color: '#000' },
    UNICA:    { label: 'Selección Única',    bg: '#198754', color: '#fff' },
    MULTIPLE: { label: 'Selección Múltiple', bg: '#ffc107', color: '#000' },
  }[pregunta._tipo];

  const puedeAvanzar = seleccion !== null && !(Array.isArray(seleccion) && seleccion.length === 0);

  return (
    <div style={styles.container}>
      <div style={styles.progresoRow}>
        <span style={styles.progresoText}>
          Pregunta <strong>{indice + 1}</strong> de <strong>{preguntas.length}</strong>
        </span>
        <span style={{ ...styles.badge, background: tipoBadge.bg, color: tipoBadge.color }}>
          {tipoBadge.label}
        </span>
      </div>
      <div style={styles.barraContainer}>
        <div style={{ ...styles.barra, width: `${progresoPct}%` }}></div>
      </div>
      <div style={styles.card}>
        <h3 style={styles.enunciado}>{pregunta.enunciado}</h3>

        {pregunta._tipo === 'VF' && (
          <div style={styles.opciones}>
            {[
              { valor: 'true',  label: '✅ Verdadero', colorSel: '#198754' },
              { valor: 'false', label: '❌ Falso',     colorSel: '#dc3545' },
            ].map(op => (
              <button key={op.valor} style={{
                ...styles.opcion,
                background:  seleccion === op.valor ? op.colorSel : '#f8f9fa',
                color:       seleccion === op.valor ? 'white'     : '#212529',
                borderColor: seleccion === op.valor ? op.colorSel : '#dee2e6',
              }} onClick={() => setSeleccion(op.valor)}>
                {op.label}
              </button>
            ))}
          </div>
        )}

        {pregunta._tipo === 'UNICA' && (
          <div style={styles.opciones}>
            {['A', 'B', 'C'].map(op => (
              <button key={op} style={{
                ...styles.opcion,
                background:  seleccion === op ? '#0d6efd' : '#f8f9fa',
                color:       seleccion === op ? 'white'   : '#212529',
                borderColor: seleccion === op ? '#0d6efd' : '#dee2e6',
              }} onClick={() => setSeleccion(op)}>
                <strong>{op})</strong> {pregunta[`opcion${op}`]}
              </button>
            ))}
          </div>
        )}

        {pregunta._tipo === 'MULTIPLE' && (
          <div style={styles.opciones}>
            <small style={{ color: '#888', marginBottom: 8, display: 'block' }}>☑️ Puedes marcar varias opciones</small>
            {['A', 'B', 'C', 'D'].map(op => {
              const sels    = seleccion || [];
              const marcada = sels.includes(op);
              return (
                <button key={op} style={{
                  ...styles.opcion,
                  background:  marcada ? '#ffc107' : '#f8f9fa',
                  color:       '#212529',
                  borderColor: marcada ? '#e0a800' : '#dee2e6',
                  fontWeight:  marcada ? 'bold'    : 'normal',
                }} onClick={() => {
                  if (marcada) setSeleccion(sels.filter(s => s !== op));
                  else         setSeleccion([...sels, op]);
                }}>
                  <strong>{op})</strong> {pregunta[`opcion${op}`]}
                </button>
              );
            })}
          </div>
        )}

        <button style={{
          ...styles.btnSiguiente,
          opacity: puedeAvanzar ? 1 : 0.4,
          cursor:  puedeAvanzar ? 'pointer' : 'not-allowed',
        }} disabled={!puedeAvanzar} onClick={handleSiguiente}>
          {indice + 1 >= preguntas.length ? '🏁 Finalizar y ver nota' : 'Siguiente →'}
        </button>
      </div>
    </div>
  );
}

const styles = {
  container:      { padding: '20px', maxWidth: '700px', margin: '0 auto' },
  center:         { display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', minHeight: '80vh', padding: 20 },
  spinner:        { width: 50, height: 50, border: '5px solid #e9ecef', borderTop: '5px solid #0d6efd', borderRadius: '50%', animation: 'spin 1s linear infinite' },
  progresoRow:    { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 10 },
  progresoText:   { fontSize: 15, color: '#495057' },
  badge:          { padding: '4px 12px', borderRadius: 20, fontSize: 12, fontWeight: 'bold' },
  barraContainer: { background: '#e9ecef', borderRadius: 5, height: 10, marginBottom: 20 },
  barra:          { background: '#0d6efd', height: 10, borderRadius: 5, transition: 'width 0.4s ease' },
  card:           { background: 'white', padding: '30px', borderRadius: 12, boxShadow: '0 4px 15px rgba(0,0,0,0.1)' },
  enunciado:      { marginBottom: 25, fontSize: 20, lineHeight: 1.6, color: '#212529' },
  opciones:       { display: 'flex', flexDirection: 'column', gap: 12, marginBottom: 25 },
  opcion:         { padding: '14px 18px', border: '2px solid', borderRadius: 8, cursor: 'pointer', fontSize: 16, textAlign: 'left', transition: 'all 0.15s' },
  btnSiguiente:   { width: '100%', padding: 15, background: '#212529', color: 'white', border: 'none', borderRadius: 8, fontSize: 18, transition: 'opacity 0.2s' },
  resultadoCard:  { background: 'white', padding: '40px', borderRadius: 16, boxShadow: '0 8px 30px rgba(0,0,0,0.12)', textAlign: 'center', maxWidth: 480, width: '100%' },
  notaGrande:     { fontSize: 80, fontWeight: 'bold', lineHeight: 1, margin: '10px 0' },
  statsRow:       { display: 'flex', justifyContent: 'center', gap: 30, margin: '20px 0' },
  statBox:        { display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 4 },
  btnPrimario:    { padding: '12px 24px', background: '#0d6efd', color: 'white', border: 'none', borderRadius: 8, fontSize: 15, cursor: 'pointer' },
  btnSecundario:  { padding: '12px 24px', background: '#6c757d', color: 'white', border: 'none', borderRadius: 8, fontSize: 15, cursor: 'pointer' },
};

export default RealizarTest;