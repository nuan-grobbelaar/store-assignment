import { Link, useParams } from "react-router-dom";
import { useProduct } from "../hooks/useProducts.ts";
import ProductControls from "../components/product/ProductControls.tsx";

function ProductPage() {
  const { id } = useParams<{ id: string }>();
  const { data: product, isLoading, error } = useProduct(id);

  if (isLoading) return <p className="product-page__status">Loading product...</p>;
  if (error) return <p className="product-page__status">Error: {error.message}</p>;
  if (!product) return <p className="product-page__status">Product not found</p>;

  return (
    <section className="product-page">
      <article className="product-page__square">
        <Link to="/" className="product-page__back">&larr; Back</Link>
        <div className="product-page__image">
          <img src={product.imageUrl} alt={product.name} />
        </div>
        <div className="product-page__info">
          <div className="product-page__meta">
            {product.brand && <p className="product-page__brand">{product.brand}</p>}
            <h1 className="product-page__name">{product.name}</h1>
            <p className="product-page__price">€{product.price}</p>
            <p className="product-page__description">{product.description}</p>
            <p className="product-page__stock">
              {product.stock > 0 ? `${product.stock} in stock` : "Out of stock"}
            </p>
          </div>
          <ProductControls product={product} />
        </div>
      </article>
    </section>
  );
}

export default ProductPage;
