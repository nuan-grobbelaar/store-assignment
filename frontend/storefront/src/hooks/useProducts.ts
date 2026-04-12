import { useQuery } from '@tanstack/react-query'

export interface Product {
  id: number
  name: string
  description: string
  price: number
  category: string
  brand: string
  stock: number
  imageUrl: string
}

const API_BASE_URL = 'http://localhost:8080/api/products'

export const useProducts = () => {
  return useQuery({
    queryKey: ['products'],
    queryFn: async (): Promise<Product[]> => {
      const response = await fetch(`${API_BASE_URL}`)
      if (!response.ok) {
        throw new Error('Failed to fetch products')
      }
      return response.json()
    },
  })
}

