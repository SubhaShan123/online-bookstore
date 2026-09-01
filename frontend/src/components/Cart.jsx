export default function Cart({ items, onUpdateQuantity, onRemove, onCheckout }) {
  const total = items.reduce((sum, item) => sum + item.price * item.quantity, 0);

  return (
    <aside className="cart-panel" aria-label="Shopping cart">
      <div className="cart-heading"><h2>Your cart</h2><span>{items.length} titles</span></div>
      {!items.length ? <p className="empty-cart">Your cart is waiting for a great story.</p> : (
        <>
          <ul className="cart-items">
            {items.map((item) => (
              <li key={item.id}>
                <div><strong>{item.title}</strong><span>₹{item.price.toFixed(2)} each</span></div>
                <div className="cart-actions">
                  <div className="quantity-control" aria-label={`Quantity for ${item.title}`}>
                    <button type="button" onClick={() => onUpdateQuantity(item.id, item.quantity - 1)} aria-label="Decrease quantity">−</button>
                    <span>{item.quantity}</span>
                    <button type="button" onClick={() => onUpdateQuantity(item.id, item.quantity + 1)} aria-label="Increase quantity">+</button>
                  </div>
                  <button className="text-button" type="button" onClick={() => onRemove(item.id)}>Remove</button>
                </div>
              </li>
            ))}
          </ul>
          <div className="cart-total"><span>Subtotal</span><strong>₹{total.toFixed(2)}</strong></div>
          <button className="checkout-button" type="button" onClick={onCheckout} disabled={!items.length}>Checkout</button>
        </>
      )}
    </aside>
  );
}
