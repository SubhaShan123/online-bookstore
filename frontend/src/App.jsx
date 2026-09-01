import { useEffect, useState } from 'react';
import BookList from './components/BookList';
import Cart from './components/Cart';
import CheckOutSummary from './components/CheckOutSummary';
import Login from './components/Login';
import { createOrder, getBooks, login, register } from './services/api';

export default function App() {
  const [books, setBooks] = useState([]);
  const [cart, setCart] = useState([]);
  const [username, setUsername] = useState(localStorage.getItem('bookstore-username') || '');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [order, setOrder] = useState(null);

  useEffect(() => {
    getBooks().then(setBooks).catch(() => setError('We could not load books. Make sure the backend is running on port 8080.')).finally(() => setLoading(false));
  }, []);

  function addToCart(book) {
    setCart((items) => {
      const existing = items.find((item) => item.id === book.id);
      return existing ? items.map((item) => item.id === book.id ? { ...item, quantity: item.quantity + 1 } : item) : [...items, { ...book, quantity: 1 }];
    });
  }
  function updateQuantity(id, quantity) { setCart((items) => quantity < 1 ? items.filter((item) => item.id !== id) : items.map((item) => item.id === id ? { ...item, quantity } : item)); }
  async function authenticate(mode, credentials) {
    const response = mode === 'login' ? await login(credentials) : await register(credentials);
    const signedInUser = response.username || credentials.username;
    localStorage.setItem('bookstore-username', signedInUser);
    setUsername(signedInUser);
    setError('');
  }
  async function checkout() {
    if (!cart.length) return setError('Add at least one book before checking out.');
    if (!username.trim()) return setError('Please sign in or create an account before checking out.');
    const bookIds = cart.flatMap((item) => Array(item.quantity).fill(item.id));
    if (bookIds.some((id) => !Number.isInteger(id))) return setError('One or more selected books are invalid. Please refresh the catalogue.');
    try { const completedOrder = await createOrder(username, bookIds); setOrder(completedOrder); setCart([]); setError(''); }
    catch (requestError) { setError(requestError.message || 'Checkout could not be completed.'); }
  }

  return (
    <div className="app-shell">
      <header><a className="brand" href="#top">Paper <em>&</em> Pine</a><Login username={username} onAuthenticated={authenticate} onError={setError} /></header>
      <main id="top">
        <section className="hero"><p className="eyebrow">Independent stories, delivered</p><h1>Find a book worth <em>keeping.</em></h1><p>Thoughtful titles for your next quiet hour.</p></section>
        {error && <div className="error-message" role="alert">{error}<button type="button" onClick={() => setError('')}>×</button></div>}
        <div className="store-layout"><section><div className="section-title"><h2>Browse the shelf</h2><span>{books.length} available</span></div><BookList books={books} loading={loading} onAddToCart={addToCart} /></section><Cart items={cart} onUpdateQuantity={updateQuantity} onRemove={(id) => updateQuantity(id, 0)} onCheckout={checkout} /></div>
      </main>
      <CheckOutSummary order={order} onClose={() => setOrder(null)} />
    </div>
  );
}
