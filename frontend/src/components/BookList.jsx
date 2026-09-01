export default function BookList({ books, loading, onAddToCart }) {
  if (loading) return <p className="state-message">Loading the bookshop…</p>;
  if (!books.length) return <p className="state-message">No books are available right now.</p>;

  return (
    <section className="catalogue" aria-label="Book catalogue">
      {books.map((book) => (
        <article className="book-card" key={book.id}>
          <div className="book-cover" aria-hidden="true"><span>{book.title.charAt(0)}</span></div>
          <div className="book-details">
            <p className="eyebrow">Featured read</p>
            <h3>{book.title}</h3>
            <p className="author">by {book.author}</p>
            <div className="book-footer">
              <strong>₹{Number(book.price).toFixed(2)}</strong>
              <button type="button" onClick={() => onAddToCart(book)}>Add to cart</button>
            </div>
          </div>
        </article>
      ))}
    </section>
  );
}
