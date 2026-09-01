export default function CheckOutSummary({ order, onClose }) {
  if (!order) return null;
  return (
    <div className="modal-backdrop" role="presentation">
      <section className="checkout-summary" role="dialog" aria-modal="true" aria-labelledby="order-title">
        <p className="eyebrow">Order confirmed</p>
        <h2 id="order-title">Your next chapter is on its way.</h2>
        <p>Thank you, <strong>{order.customerUsername}</strong>. Your order contains {order.bookIds.length} book{order.bookIds.length === 1 ? '' : 's'}.</p>
        <div className="order-total"><span>Total charged</span><strong>₹{Number(order.totalAmount).toFixed(2)}</strong></div>
        <button type="button" onClick={onClose}>Continue browsing</button>
      </section>
    </div>
  );
}
