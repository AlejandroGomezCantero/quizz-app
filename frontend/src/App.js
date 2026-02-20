import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Home from './pages/Home';
import Usuarios from './pages/Usuarios';
import RealizarTest from './pages/RealizarTest';
import Resultados from './pages/Resultados';

function ProtectedRoute({ children }) {
  const token = localStorage.getItem('token');
  return token ? children : <Navigate to="/login" />;
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<ProtectedRoute><Home /></ProtectedRoute>} />
        <Route path="/usuarios" element={<ProtectedRoute><Usuarios /></ProtectedRoute>} />
        <Route path="/test/realizar" element={<ProtectedRoute><RealizarTest /></ProtectedRoute>} />
        <Route path="/test/resultados" element={<ProtectedRoute><Resultados /></ProtectedRoute>} />
        {/* Redirigir /test/configurar a /test/realizar por si acaso */}
        <Route path="/test/configurar" element={<Navigate to="/test/realizar" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;