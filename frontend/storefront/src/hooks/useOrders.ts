import { useQuery } from "@tanstack/react-query";

export interface OrderItem {
  productId: number;
  productName: string;
  quantity: number;
}

export interface Order {
  id: number;
  customerEmail: string;
  items: OrderItem[];
  total: number;
  createdAt: string;
}

const API_BASE_URL = "http://localhost:8080/api/orders";

export const useOrders = () => {
  return useQuery({
    queryKey: ["orders"],
    queryFn: async (): Promise<Order[]> => {
      const response = await fetch(`${API_BASE_URL}`);
      if (!response.ok) {
        throw new Error("Failed to fetch orders");
      }
      return response.json();
    },
  });
};
