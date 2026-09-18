import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import {
  AuditEvent,
  Dashboard,
  Movement,
  Order,
  OrderStatus,
  Product,
} from "./models";
@Injectable({ providedIn: "root" })
export class ApiService {
  constructor(private http: HttpClient) {}
  dashboard() {
    return this.http.get<Dashboard>("/api/dashboard");
  }
  products() {
    return this.http.get<Product[]>("/api/products");
  }
  createProduct(body: any) {
    return this.http.post<Product>("/api/products", body);
  }
  updateProduct(id: string, body: any) {
    return this.http.put<Product>(`/api/products/${id}`, body);
  }
  movements() {
    return this.http.get<Movement[]>("/api/inventory/movements");
  }
  adjustStock(productId: string, quantity: number, reason: string) {
    return this.http.post<void>("/api/inventory/adjust", {
      productId,
      quantity,
      reason,
    });
  }
  orders() {
    return this.http.get<Order[]>("/api/orders");
  }
  createOrder(body: any) {
    return this.http.post<Order>("/api/orders", body);
  }
  updateStatus(id: string, status: OrderStatus) {
    return this.http.patch<Order>(`/api/orders/${id}/status`, { status });
  }
  audit() {
    return this.http.get<AuditEvent[]>("/api/audit");
  }
}
