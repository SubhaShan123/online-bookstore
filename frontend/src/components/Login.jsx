import { useState } from 'react';

export default function Login({ username, onAuthenticated, onError }) {
  const [mode, setMode] = useState('login');
  const [form, setForm] = useState({ username: '', password: '' });
  const [fieldErrors, setFieldErrors] = useState({});
  const [submitting, setSubmitting] = useState(false);

  async function submit(event) {
    event.preventDefault();
    const errors = {};
    if (!form.username.trim()) errors.username = 'Username is required.';
    if (!form.password.trim()) errors.password = 'Password is required.';
    setFieldErrors(errors);
    if (Object.keys(errors).length) return;
    setSubmitting(true);
    try {
      await onAuthenticated(mode, { username: form.username.trim(), password: form.password });
      setForm({ username: '', password: '' });
    } catch (error) {
      onError(error.message);
    } finally { setSubmitting(false); }
  }

  if (username) return <div className="account-status">Signed in as <strong>{username}</strong></div>;

  return (
    <form className="login-form" onSubmit={submit} noValidate>
      <div className="mode-toggle">
        <button type="button" className={mode === 'login' ? 'active' : ''} onClick={() => { setMode('login'); setFieldErrors({}); }}>Sign in</button>
        <button type="button" className={mode === 'register' ? 'active' : ''} onClick={() => { setMode('register'); setFieldErrors({}); }}>Create account</button>
      </div>
      <label className="form-field"><input aria-label="Username" aria-invalid={Boolean(fieldErrors.username)} placeholder="Username" value={form.username} onChange={(event) => { setForm({ ...form, username: event.target.value }); setFieldErrors({ ...fieldErrors, username: undefined }); }} />{fieldErrors.username && <span>{fieldErrors.username}</span>}</label>
      <label className="form-field"><input aria-label="Password" aria-invalid={Boolean(fieldErrors.password)} placeholder="Password" type="password" value={form.password} onChange={(event) => { setForm({ ...form, password: event.target.value }); setFieldErrors({ ...fieldErrors, password: undefined }); }} />{fieldErrors.password && <span>{fieldErrors.password}</span>}</label>
      <button type="submit" disabled={submitting}>{submitting ? 'Please wait…' : mode === 'login' ? 'Sign in' : 'Register'}</button>
    </form>
  );
}
