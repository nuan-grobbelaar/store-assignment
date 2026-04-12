import './styles/App.scss'
import { useProducts } from './hooks/useProducts'

function App() {
  const { data: products, isLoading, error } = useProducts()

  return (
    <>
      <section id="products">
        {isLoading && <p>Loading products...</p>}
        {error && <p>Error loading products: {(error as Error).message}</p>}
        {products && products.length > 0 ? (
          <div>
            {products.map((product) => (
              <div key={product.id}>
                <p>{product.name}</p>
              </div>
            ))}
          </div>
        ) : (
          !isLoading && <p>No products available</p>
        )}
      </section>
    </>
  )
}

export default App
