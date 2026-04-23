import { createContext, useContext, useEffect, useState, type ReactNode } from "react";
import type { Product } from "../hooks/useProducts.ts";

export interface CartItem {
  product: Product;
  quantity: number;
}

interface CartContextValue {
  items: CartItem[];
  addToCart: (product: Product) => void;
  decrement: (product: Product) => void;
  clearCart: () => void;
  isOpen: boolean;
  status: OrderStatus;
  setStatus: (o: OrderStatus) => void;
  openCart: () => void;
  closeCart: () => void;
}

type OrderStatus = "idle" | "loading" | "success" | "error";

const CartContext = createContext<CartContextValue | undefined>(undefined);

const STORAGE_KEY = "cart";

export function CartProvider({ children }: { children: ReactNode }) {
  const [items, setItems] = useState<CartItem[]>(() => {
    try {
      const stored = localStorage.getItem(STORAGE_KEY);
      return stored ? (JSON.parse(stored) as CartItem[]) : [];
    } catch {
      return [];
    }
  });

  const [isOpen, setIsOpen] = useState(false);
  const [status, setStatus] = useState<OrderStatus>("idle");

  useEffect(() => {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(items));
  }, [items]);

  const openCart = () => {
    setIsOpen(true);
    setStatus("idle");
  };

  const addToCart = (product: Product) => {
    setItems((prev) => {
      const existing = prev.find((i) => i.product.id === product.id);
      if (existing) {
        return prev.map((i) =>
          i.product.id === product.id ? { ...i, quantity: i.quantity + 1 } : i
        );
      }
      return [...prev, { product, quantity: 1 }];
    });
  };

  const decrement = (product: Product) => {
    setItems((prev) =>
      prev
        .map((i) => (i.product.id === product.id ? { ...i, quantity: i.quantity - 1 } : i))
        .filter((i) => i.quantity > 0)
    );
  };

  const clearCart = () => {
    setItems([]);
    setIsOpen(false);
  };

  return (
    <CartContext.Provider
      value={{
        items,
        addToCart,
        decrement,
        clearCart,
        isOpen,
        setStatus,
        status,
        openCart,
        closeCart: () => setIsOpen(false),
      }}
    >
      {children}
    </CartContext.Provider>
  );
}

export function useCart() {
  const ctx = useContext(CartContext);
  if (!ctx) throw new Error("useCart must be used within CartProvider");
  return ctx;
}
